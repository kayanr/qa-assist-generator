package com.qaassist.generator;

import com.qaassist.generator.engine.model.TestCase;

public class ConsolePrinter {
// ANSI colour constants
public static final String RESET  = "\u001B[0m";
public static final String GREEN  = "\u001B[32m";
public static final String RED    = "\u001B[31m";
public static final String YELLOW = "\u001B[33m";
public static final String CYAN   = "\u001B[36m";
public static final String BOLD   = "\u001B[1m";

public static void printFeatureHeader(String featureType, int count, String priority) {
    System.out.println("\n┌─────────────────────────────────────────────┐");
    System.out.printf( "│  %-12s │  %2d test cases  │  %-8s │%n", featureType, count, priority);
    System.out.println("└─────────────────────────────────────────────┘");
}

public static void printTestCase(TestCase tc) {
    String colour = switch (tc.getTestType()) {
        case HAPPY_PATH -> GREEN;
        case NEGATIVE   -> RED;
        case BOUNDARY   -> YELLOW;
        case EDGE       -> CYAN;
    };
    System.out.println("  " + colour + BOLD + "[" + tc.getId() + "]  " + tc.getTitle() + "  " + tc.getTestType() + RESET);
    System.out.println("  ────────────────────────────────────────────────────────────");
    System.out.println("  Precondition : " + tc.getPrecondition());
    System.out.println("  Steps        : " + tc.getSteps().replace("\n", "\n                 "));
    System.out.println("  Expected     : " + tc.getExpectedResult());
    System.out.println();
}

public static void printSummary(int totalTypes, int totalCases, long elapsedMs) {
    System.out.println("╔══════════════════════════════════════════════╗");
    System.out.println("║  SUMMARY                                     ║");
    System.out.printf( "║  Feature Types  : %-26d ║%n", totalTypes);
    System.out.printf( "║  Total Cases    : %-26d ║%n", totalCases);
    System.out.printf( "║  Generated in   : %-23s    ║%n", elapsedMs + "ms");
    System.out.println("╚══════════════════════════════════════════════╝");
}

    public static void printBanner() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║      QA ASSIST GENERATOR  v0.1.0         ║");
        System.out.println("║      Plain Java Engine — Demo Mode       ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }
}
