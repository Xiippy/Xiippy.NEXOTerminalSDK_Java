// *******************************************************************************************
// Copyright © 2019 Xiippy.ai. All rights reserved. Australian patents awarded. PCT patent pending.
//
// NOTES:
//
// - No payment gateway SDK function is consumed directly. Interfaces are defined out of such
//   interactions and then the interface is implemented for payment gateways. Design the interface
//   with the most common members and data structures between different gateways.
// - A proper factory or provider must instantiate an instance of the interface that is interacted with.
// - Any major change made to SDKs should begin with the C# SDK with the mindset to keep the
//   high-level syntax, structures and class names the same to minimise porting efforts to other languages.
//   Do not use language specific features that do not exist in other languages.
// - Pascal Case for naming conventions should be used for all languages.
// - No secret or passwords or keys must exist in the code when checked in.
//
// *******************************************************************************************

package io.github.xiippy.nexoterminalsdk.common;

/**
 * Tariff cost category label constants.
 * Mirrors C# TariffCostCategories.
 */
public final class TariffCostCategories {

    private TariffCostCategories() {
        // Utility class – no instantiation.
    }

    public static final String Fares                 = "Fares";
    public static final String DistanceCosts         = "Distance";
    public static final String TimeCosts             = "Time";
    public static final String AirportCosts          = "Airport";
    public static final String ExtraCosts            = "Extras";
    public static final String TollsCosts            = "Tolls";
    public static final String Surcharge             = "Surcharges";
    public static final String Others                = "Others";
    public static final String Lifting               = "Lifting";
    public static final String LiftingDriverPayouts  = "LiftingDriverPayout";
    public static final String TSSDiscounts          = "TSSDiscounts";
}
