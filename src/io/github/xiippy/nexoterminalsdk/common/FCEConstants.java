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
 * FCE (Fixed Cost Element) statement item identifier constants.
 * Mirrors C# FCEConstants.
 */
public final class FCEConstants {

    private FCEConstants() {
        // Utility class – no instantiation.
    }

    public static final String StatementItemIdentifier_BookingFee                  = "Booking Fees";
    public static final String StatementItemIdentifier_CPVLevyRecoveryFees         = "CPV Levy Recovery Fee";
    public static final String StatementItemIdentifier_HighOccupancyFee            = "High Occupancy Fee";
    public static final String StatementItemIdentifier_LiftingFee                  = "Lifting Fee";
    public static final String StatementItemIdentifier_LiftingFeePaymentToDriver   = "Lifting Fee Payment to Driver";
    public static final String StatementItemIdentifier_CleaningFees                = "Cleaning Fees";
    public static final String StatementItemIdentifier_HolidayFee                  = "Holiday Fee";
    public static final String StatementItemIdentifier_WheelchairAccessibleTaxiWATfee = "Wheelchair Accessible Taxi (WAT) fee";
    public static final String TaxiSubsidyClaimStatementItemID                     = "Taxi Subsidy Scheme Discount";
    public static final String TaxiSubsidyClaimStatementItemDescription            = "Taxi Subsidy Scheme Discount";
    public static final String StatementItemIdentifier_PassengerServiceLevyFees    = "Passenger Service Levy Fees";
    public static final String StatementItemIdentifier_PeakTimeHireChargeFees      = "Peak Time Hire Charge";
}
