package com.hussein.samples.kidstand;

import com.hussein.samples.kidstand.model.GamePlayResult;
import com.hussein.samples.kidstand.service.KidStandingGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
@Import(ApplicationConfig.class)
public class KidStandApplication implements CommandLineRunner {
	@Autowired
	private KidStandingGameService gameService;

	public static void main(String[] args) {
		SpringApplication.run(KidStandApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter n: ");
		final int n = input.nextInt();
		System.out.println("Please enter k: ");
		final int k = input.nextInt();

		GamePlayResult playResult = gameService.play(n, k);
		System.out.println(String.format("The winner kid id is %s, and the eliminated kids are %s",
				playResult.getWinningKidId(),
				playResult.getEliminatedKidIds().stream()
						.map(Objects::toString)
						.collect( Collectors.joining( " -> " ) )));
	}
}
