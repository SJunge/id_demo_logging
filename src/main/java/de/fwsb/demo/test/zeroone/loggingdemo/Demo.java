package de.fwsb.demo.test.zeroone.loggingdemo;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.IntStream;


/**  */
public final
class Demo
{
    private static final String LOG_MESSAGE_TEXT = "myMsgText: {}";


    public static void
    main( @NonNull final String[] ignored )
    {
        Configurator.setRootLevel( Level.DEBUG );

        IntStream.rangeClosed( 1, 10 )
                .unordered()
                .sequential()
                .forEach( ignoredValue -> Demo.doDemoLogRoutine() );
    }

    private static void
    doDemoLogRoutine()
    {
        final var aRndValue = Math.random();
//        System.out.println( "a = " + aBooleanValue );

        final var myRndText = (aRndValue < .5 )
                ? "m"
                : (aRndValue > .75)
                  ? "b"
                  : (aRndValue > .51)
                    ? "a"
                    : "something else";
//        System.out.println( "myRndText = " + myRndText );

        // final var myFxPtr = (Supplier<String>) () -> "StringSupplier";
        final Supplier<String> myFxPtr = () -> "StringSupplier";

        final var result =
                switch ( myRndText )
                {
                    case "m": yield "Hello, World!";
                    case "b": yield myFxPtr;
                    case "a": yield Integer.valueOf( 3 ).byteValue();
                    default:  yield """
                                    nothing to
                                      say so far
                                        ..... .... .
                                     """.indent(7);
                };
        System.out.println("result.getClass() = " + result.getClass());
        System.out.println("result = " + result);

        final var log = LogManager.getLogger( Demo.class );

//        // unschönes If-Wrappen
//        if ( log.isTraceEnabled() )
//        {
            System.out.println( ">>>>> Log1-plainCall" );
            log.trace( LOG_MESSAGE_TEXT,
                       Demo.doSomethingWithIntensiveComputation( result ) );

            System.out.println( ">>>>> Log2-withLambda" );
            log.trace( LOG_MESSAGE_TEXT,
                       () -> Demo.doSomethingWithIntensiveComputation( result ) );
//        }
    }


    static
    <T> T
    doSomethingWithIntensiveComputation( @NonNull final T someObject )
    {
        Objects.requireNonNull( someObject );

        System.out.println( "...sleeping with: \t" + someObject.getClass() );
        Demo.sleep();

        return someObject;
    }


    private static void
    sleep()
    {
        try
        { Thread.sleep(2_000 ); }
        catch ( final Throwable ignored ) // demo
        { /* ignored */ }
    }


}
