package com.min.reactor.stepverify;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class Part03StepVerifier {

    // TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then completes successfully.
    @Test
    void expectFooBarCompleteTest() {
        final Flux<String> flux = Flux.just("foo", "bar");
        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .verifyComplete();
    }

//========================================================================================

    // TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then a RuntimeException error.
    @Test
    void expectFooBarError() {
        final Flux<String> flux = Flux.just("foo","par");

        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .expectError(RuntimeException.class);
    }

//========================================================================================

    // TODO Use StepVerifier to check that the flux parameter emits a User with "swhite"username
    // and another one with "jpinkman" then completes successfully.
    @Test
    void expectSkylerJesseComplete() {
        // given
        final Flux<User> flux = Flux.just(new User("swhite"), new User("jpinkman"));

        StepVerifier.create(flux)
                .expectNextMatches(u -> u.name.equals("swhite"))
                .expectNextMatches(u -> u.name.equals("jpinkman"))
                .verifyComplete();

        final Flux<String> names = flux.map(user -> user.name);

        StepVerifier.create(names)
                .expectNext("swhite")
                .expectNext("jpinkman")
                .verifyComplete();

        StepVerifier.create(flux)
                .assertNext(user -> assertThat(user.getName()).isEqualTo("swhite"))
                .assertNext(user -> assertThat(user.getName()).isEqualTo("jpinkman"))
                .verifyComplete();
        //fail();
    }

//========================================================================================

    // TODO Expect 10 elements then complete and notice how long the test takes.
    @Test
    void expect10Elements() {
        Flux<Long> take10 = Flux.interval(Duration.ofMillis(100))
                .take(10);

        StepVerifier.create(take10)
                .expectNextCount(10)
                .verifyComplete();

        StepVerifier.create(take10)
                .expectTimeout(Duration.ofMillis(100*10));
    }

//========================================================================================

    // TODO Expect 3600 elements at intervals of 1 second, and verify quicker than 3600s
    // by manipulating virtual time thanks to StepVerifier#withVirtualTime, notice how long the test takes
    @Test
    void expect3600Elements(Supplier<Flux<Long>> supplier) {
        StepVerifier.withVirtualTime(() ->
                Mono.delay(Duration.ofHours(3)))
                .expectSubscription()
                .expectNoEvent(Duration.ofHours(2))
                .thenAwait(Duration.ofHours(1))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    private void fail() {
        throw new AssertionError("workshop not implemented");
    }

    static class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
