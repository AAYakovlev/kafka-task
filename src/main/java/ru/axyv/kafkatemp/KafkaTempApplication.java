package ru.axyv.kafkatemp;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import ru.axyv.kafkatemp.model.Msg;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class KafkaTempApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaTempApplication.class, args);
	}

	@Autowired
	private KafkaTemplate<String, Msg> template;
	@Autowired
	private ApplicationContext context;

	private final CountDownLatch latch = new CountDownLatch(3);

	@Override
	public void run(String... args) throws Exception {
//		this.template.send("myTopic1", new Msg(1L, "1p"));
//		this.template.send("myTopic1", new Msg(2L, "3p"));
//		this.template.send("myTopic1", new Msg(3L, "3p"));
//		latch.await(60, TimeUnit.SECONDS);
//		log.info("All received");
	}
}
