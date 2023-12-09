package util;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.google.gson.Gson;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.formula.Formula;
import smile.data.vector.DoubleVector;
import smile.regression.RandomForest;

public class Predict {
	// 測試用
	public static void main(String[] args) throws IOException {
		double predictPrice1 = simpleRegression("2330", "20231130");
		System.out.println("Regression 預測下一個值（明日股價）: " + predictPrice1);
		
		double predictPrice2 = smile("2330", "20231130");
		System.out.println("Smile 預測下一個值（明日股價）: " + predictPrice2);
	}
	
	// Smile 機器學習
	public static double smile(String stockNo, String date) throws IOException {
    	// 使用GetPrice類的getClosingPrice方法獲取這支股票過去 N 天的收盤價格
        double[] prices = getClosingPrice(stockNo, date);
        // 使用GetPrice類的getVolume方法獲取這支股票過去 N 天的成交量
        double[] volumes = getVolume(stockNo, date);

        // 創建一個DataFrame來存儲股票的價格和成交量數據
        // 首先創建一個只包含價格的DataFrame
        DataFrame data = DataFrame.of(DoubleVector.of("Price", prices));
        // 將成交量數據與價格數據合併，形成一個包含兩個特徵的DataFrame
        data = data.merge(DoubleVector.of("Volume", volumes));

        // 定義數據集中的響應變量（即我們想要預測的目標變量），這裡是"Price"
        Formula formula = Formula.lhs("Price");

        // 使用隨機森林算法建立回歸模型。這裡指定"Price"為因變量(你要觀察的)，其餘列作為自變量(可能會影響價格的因素例如：成交量)
        RandomForest model = RandomForest.fit(formula, data);

        // 獲取數據集中的最後一條數據（最後一天的價格和成交量），以預測下一個值（明日股價）
        Tuple lastRow = data.stream().skip(data.nrows() - 1).findFirst().orElse(null);

        // 使用隨機森林模型對最後一條數據進行預測，得出的forecast即為預測的明日股價
        double predictedPrice = model.predict(lastRow);
        
     	return predictedPrice;
    }
	// 簡單回歸
	public static double simpleRegression(String stockNo, String date) throws IOException {
		// 假設有一組股票價格，這裡用價格和對應的時間（天）表示
        double[][] data = getTimeAndClosingPrice(stockNo, date);

        // 使用 Apache Commons Math 庫中的 SimpleRegression 進行線性回歸
        SimpleRegression regression = new SimpleRegression();

        // 添加數據點
        for (double[] dataPoint : data) {
            regression.addData(dataPoint[0], dataPoint[1]);
        }

        // 擬合模型
        regression.regress();

        // 使用模型預測未來的價格
        double predictedPrice = regression.predict(data.length + 1);
        return predictedPrice;
	}
	// 從台灣證交所API獲取股票資訊的JSON字符串
	private static String getJsonString(String stockNo, String date) throws IOException {
		// 股票資訊API的URL
		String path = "https://www.twse.com.tw/rwd/zh/afterTrading/STOCK_DAY?date=" + date + "&stockNo=" + stockNo + "&response=json";
		try (Scanner scanner = new Scanner(new URL(path).openStream()).useDelimiter("\\A")) {
			return scanner.hasNext() ? scanner.next() : "";
		}
	}
	// 獲取指定股票的時間序列和收盤價格
	public static double[][] getTimeAndClosingPrice(String stockNo, String date) throws IOException {
		// 調用getJsonString獲取JSON數據
		String jsonString = getJsonString(stockNo, date);

		// 使用Gson解析JSON數據
		Gson gson = new Gson();
		TradingData tradingData = gson.fromJson(jsonString, TradingData.class);

		// 創建一個二維數組來儲存時間和價格
		double[][] prices = new double[tradingData.data.size()][2];

		for (int i = 0; i < tradingData.data.size(); i++) {
			List<String> dailyData = tradingData.data.get(i);
			prices[i][0] = i + 1; // 時間流水號
			prices[i][1] = Double.parseDouble(dailyData.get(6).replace(",", "")); // 收盤價在索引6的位置
		}

		return prices;
	}
	
	// 單獨獲取指定股票的收盤價格
	public static double[] getClosingPrice(String stockNo, String date) throws IOException {
		// 調用getJsonString獲取JSON數據
		String jsonString = getJsonString(stockNo, date);
		Gson gson = new Gson();
		TradingData tradingData = gson.fromJson(jsonString, TradingData.class);

		// 從tradingData中提取收盤價格，並轉換為double數組
		return tradingData.data.stream().mapToDouble(dailyData -> Double.parseDouble(dailyData.get(6).replace(",", ""))) // 收盤價是在索引6的位置
				.toArray();
	}

	// 獲取指定股票的成交量
	public static double[] getVolume(String stockNo, String date) throws IOException {
		// 調用getJsonString獲取JSON數據
		String jsonString = getJsonString(stockNo, date);
		Gson gson = new Gson();
		TradingData tradingData = gson.fromJson(jsonString, TradingData.class);

		// 從tradingData中提取成交量，並轉換為double數組
		return tradingData.data.stream().mapToDouble(dailyData -> Double.parseDouble(dailyData.get(8).replace(",", ""))) // 成交量是在索引8的位置
				.toArray();
	}
		
	// 內部類，用於解析從API獲取的股票交易數據
	private class TradingData {
		String stat;
		String date;
		String title;
		List<String> fields;
		List<List<String>> data;
		List<String> notes;
		int total;
	}
	
}

