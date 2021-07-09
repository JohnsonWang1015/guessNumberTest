package com.sample;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainClass {
	
	static int min = 1;
	static int max = 100;
	static int guess = 0;
	static List<Long> time_computer = new ArrayList<>();
	static List<Long> time_binary = new ArrayList<>();
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int keyin = 0; // 選項
		
		do {
			System.out.print("1)電腦正常猜 2)電腦二分搜尋猜 3)離開:");
			keyin = scanner.nextInt();
			if(keyin < 1 || keyin > 3) {
				System.out.println("輸入錯誤，請再輸入一次!");
				continue;
			}else {
				switch(keyin) {
					case 1:
						computerGuessNumber();
						break;
					case 2:
						binaryGuessNumber();
						break;
				}
			}
			
		}while(keyin != 3); // 輸入選項不等於 3 繼續
		System.out.println("==========遊戲結束==========");
		
		// 顯示時間
		System.out.print("電腦正常猜時間：");
		for(int i=0;i<time_computer.size(); i++) {
			System.out.printf("%d ", time_computer.get(i));
		}
		
		System.out.print("\n電腦二分搜尋猜時間：");
		for(int i=0;i<time_binary.size(); i++) {
			System.out.printf("%d ", time_binary.get(i));
		}
	}
	
	// 電腦正常猜數字
	public static void computerGuessNumber() {
		guess = 0;
		int random = new Random().nextInt(100) + 1;
		
		do {
			Instant t1 = Instant.now(); // 時間戳 t1
			System.out.printf("請猜 %2d ~ %3d : ", min, max);
			guess = new Random().nextInt(100) + 1;
			System.out.printf("[%d]%n", guess); // 列印猜的數字
			
			if(guess == random) {
				Instant t2 = Instant.now(); // 時間戳 t2
				Duration duration = Duration.between(t1, t2); // 計算時間差
				System.out.println("時間:" + duration.toNanos()); // 列印轉成奈秒的時間差
				
				time_computer.add(duration.toNanos()); // 儲存轉成奈秒的時間差
				
				System.out.printf("猜中了! 答案是:%d%n", random);
			}else {
				if(guess >= max) {
					max = guess;
				}else {
					min = guess;
				}
			}
		}while(guess != random);

	}
	
	// 電腦二分搜尋法猜數字
	public static void binaryGuessNumber() {
		guess = 0;
		int random = new Random().nextInt(100) + 1;
		
		int[] g = new int[100]; // 給一個 100 個空間的陣列
		for(int i=0; i<g.length; i++) {
			g[i] = (i+1); // 儲存數字 1 ~ 100
		}
		
		Instant t1 = Instant.now(); // 時間戳 t1
		
		int guess = Arrays.binarySearch(g, random) + 1; // 二分搜尋法
		System.out.printf("請猜 %2d ~ %3d : ", min, max);
		System.out.printf("[%d]%n", guess); // 列印猜的數字
		
		if(guess == random) {
			Instant t2 = Instant.now(); // 時間戳 t2
			Duration duration = Duration.between(t1, t2); // 計算時間差
			System.out.println("時間:" + duration.toNanos()); // 列印轉成奈秒的時間差
			
			time_binary.add(duration.toNanos()); // 儲存轉成奈秒的時間差
			
			System.out.printf("猜中了! 答案是:%d%n", random);
		}
		
	}
}
