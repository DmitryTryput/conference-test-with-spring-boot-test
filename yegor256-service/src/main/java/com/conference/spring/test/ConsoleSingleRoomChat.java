package com.conference.spring.test;

import com.conference.spring.reco.RecommendationServiceGrpc;
import com.conference.spring.reco.RecommendationServiceGrpc.RecommendationServiceStub;
import com.conference.spring.reco.Slowrecommendation.Question;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author tolkv
 * @version 19/03/2017
 */
@Slf4j
public class ConsoleSingleRoomChat {
  static StreamObserver<Question> client;

  public static void main(String[] args) {
    client = configureRecommendationClient("127.0.0.1", 10101);

    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      while (true) {
        System.out.print("Enter something [q-quit]: ");
        String input = br.readLine();

        if ("q".equals(input)) {
          System.out.println("Exit!");
          System.exit(0);
        }

        ask(input);

        System.out.println("input : " + input);
        System.out.println("-----------\n");
      }

    } catch (IOException e) {
      log.error("catch error", e);
    }

  }

  private static void ask(String question) {
    client.onNext(Question.newBuilder()
        .setBody(question)
        .build());
  }

  private static StreamObserver<Question> configureRecommendationClient(String host, int port) {
    ManagedChannel channel = NettyChannelBuilder.forAddress(host, port)
        .usePlaintext(true)
        .build();

    RecommendationServiceStub recommendationServiceStub = RecommendationServiceGrpc.newStub(channel);
    return recommendationServiceStub.streamRecommendation(
        new ChatHandler()
    );
  }
}
