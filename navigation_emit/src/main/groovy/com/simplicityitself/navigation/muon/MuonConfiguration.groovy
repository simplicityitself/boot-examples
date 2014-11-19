package com.simplicityitself.navigation.muon

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.muoncore.Muon
import org.muoncore.MuonBroadcastEventBuilder
import org.muoncore.extension.amqp.AmqpTransportExtension
import org.muoncore.extension.http.HttpTransportExtension
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableAutoConfiguration
@Configuration
@Slf4j
class MuonConfiguration {

  @Bean Muon getMuon() {
    log.info("Starting Muon service")

    Muon muon = new Muon(serviceIdentifer: "navigation")

    muon.registerExtension(new AmqpTransportExtension())

    muon.start()

    muon.onGet("/status", "Get the status for this service") {
      return new JsonBuilder([status:"Doing fine"]).toString()
    }

    return muon
  }

  @Bean Timer timer() {
    Timer timer = new Timer();
    timer.schedule(timerTask(), 0, 1000);
    return timer
  }

  @Bean TimerTask timerTask() {
    Muon muon = getMuon()

    new TimerTask() {
      public void run() {
        log.info("Emitting new nav data movement on muon broadcast")

        //TODO, some better data ...
        def navData = [
            deltaX:123,
            deltaY:1333
        ]

        muon.emit(MuonBroadcastEventBuilder.broadcast("locationChanged")
            .withContent(new JsonBuilder(navData).toString()).build())
      }
    }
  }
}
