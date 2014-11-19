package com.simplicityitself.navigation.muon

import com.simplicityitself.navigation.LocationEventStore
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
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

  @Bean Muon getMuon(LocationEventStore locationEventStore) {
    log.info("Starting Receiver service")

    Muon muon = new Muon(serviceIdentifer: "location")

    muon.registerExtension(new HttpTransportExtension(9986))
    muon.registerExtension(new AmqpTransportExtension())

    muon.start()

    muon.receive("locationChanged") {
      def data = new JsonSlurper().parseText(it.payload)
      locationEventStore.addEvent(data)
    }

    muon.onGet("/status", "Get the status for this service") {
      return new JsonBuilder(
          [
              status:"Doing fine",
              x:locationEventStore.x,
              y:locationEventStore.y
          ]).toString()
    }

    muon.onGet("/events", "Get the event stream that made up this nav history") {
      return new JsonBuilder(locationEventStore.eventStream).toString()
    }

    return muon
  }

  @Bean LocationEventStore location() {
    return new LocationEventStore()
  }
}
