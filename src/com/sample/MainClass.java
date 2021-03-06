package com.sample;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainClass {
	
	static int min = 1;
	static int max = 100;
	static int guess = 0;
	static List<Long> time_computer = new ArrayList<>();
	static List<Long> time_binary = new ArrayList<>();
	static Instant t1 = null; 	// 時間戳 t1
	static Instant t2 = null; 	// 時間戳 t2
	static Duration duration = null; 
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int keyin = 0; 			// 輸入選項
		
		do {
			int random = 0; // 答案
			
			System.out.print("1)電腦正常猜 2)電腦二分搜尋猜 3)兩者時間分析 4)離開:");
			keyin = scanner.nextInt(); 		// 輸入
			
			if(keyin < 1 || keyin > 4) { 	// 檢查輸入選項
				System.out.println("輸入錯誤，請再輸入一次!");
				continue;
			}else {
				switch(keyin) {
					case 1:
						random = new Random().nextInt(100) + 1;
						
						t1 = Instant.now();
						computerGuessNumber(random);
						t2 = Instant.now();
						duration = Duration.between(t1, t2); 		// 計算時間差
						System.out.println("時間差:" + duration.toNanos() + " 奈秒");
						time_computer.add(duration.toNanos()); 		// 列印轉成奈秒的時間差
						
						for(int i=0; i<time_computer.size(); i++) {
							if(time_computer.get(i) == 0)
								time_computer.remove(i);
						}
						
						System.out.println("----------------");
						break;
					case 2:
						random = new Random().nextInt(100) + 1;
						
						t1 = Instant.now();
						binaryGuessNumber(random);
						t2 = Instant.now();
						duration = Duration.between(t1, t2); 		// 計算時間差
						System.out.println("時間差:" + duration.toNanos() + " 奈秒");
						time_binary.add(duration.toNanos());		// 列印轉成奈秒的時間差
						
						for(int i=0; i<time_binary.size(); i++) {
							if(time_binary.get(i) == 0)
								time_binary.remove(i);
						}
						
						System.out.println("----------------");
						break;
					case 3:
						timeAnalysis();
						System.out.println("----------------");
						break;
				}
			}
			
		}while(keyin != 4);
		System.out.println("==========遊戲結束==========");
	}
	
	// 電腦正常猜數字
	private static void computerGuessNumber(int random) {	
		guess = 0;
		min = 1;
		max = 100;
		
		do {
			guess = (int)(Math.random() * (max - min + 1) + min); // 電腦用亂數猜
			System.out.printf("請猜 %2d ~ %3d : ", min, max);
			
			System.out.printf("[%d]%n", guess); // 列印猜的數字
			
			if(guess <= max && guess >= min) {
				if(guess == random) {
					System.out.printf("猜中了! 答案是:%d%n", guess);
				}else {
					if(guess > random) {
						max = guess;
					}else {
						min = guess;
					}
				}
			}else{
				System.out.printf("範圍在 %d ~ %d 之間，請再輸入一次%n", min, max);
				continue;
			}
			
		}while(guess != random);

	}
	
	// 電腦二分搜尋法猜數字
	private static void binaryGuessNumber(int random) {
		guess = 0;
		min = 1;
		max = 100;
		
		if(random == 1 || random == 100) {
			System.out.printf("請猜 %2d ~ %3d : ", min, max);
			if(random == 1) {
				guess = 1;
			}else {
				guess = 100;
			}
			System.out.printf("[%d]%n", guess);
			System.out.printf("猜中了! 答案是:%d%n", guess);
		}else {
			do {
				// 二分搜尋法
				guess= Math.round((max + min) / 2);
				System.out.printf("請猜 %2d ~ %3d : ", min, max);
				System.out.printf("[%d]%n", guess);
			
				if(guess == random) {
					System.out.printf("猜中了! 答案是:%d%n", guess);
				}else if(guess > random){
					max = guess - 1;
				}else {
					min = guess + 1;
				}
			}while(guess != random);
		}
	}
	
	private static void timeAnalysis() {
		int counter = 0; 	// 儲存時間個數
		long pc_avg = 0, bin_avg = 0, temp = 0;
		
		// 顯示時間
		System.out.println("電腦正常猜時間：" + time_computer);
		System.out.println("電腦二分搜尋猜時間：" + time_binary);
		
		// 分析時間，選時間總個數(counter)最小計算
		if(!(time_computer.isEmpty() && time_binary.isEmpty())) {
			
			counter = 0;
			counter = time_binary.size();
			for(int i=0; i<counter; i++) {
				temp += time_binary.get(i);
			}
			bin_avg = temp / counter;
			
			counter = 0;
			counter = time_computer.size();
			for(int i=0; i<counter; i++) {
				temp += time_computer.get(i);
			}
			pc_avg = temp / counter;
			
		}
		
		// 檢查是否有空集合
		if(time_computer.isEmpty()){
			System.out.printf("\n二分法搜尋，平均：%d%n", bin_avg);
		}
		if(time_binary.isEmpty()){
			System.out.printf("\n電腦搜尋，平均：%d%n", pc_avg);
		}
		
		// 判斷快慢
		if(pc_avg > bin_avg) {
			System.out.printf("\n二分法搜尋較快，平均：%d%n", bin_avg);
		}else {
			System.out.printf("\n正常搜尋較快，平均：%d%n", pc_avg);
		}
	}
}