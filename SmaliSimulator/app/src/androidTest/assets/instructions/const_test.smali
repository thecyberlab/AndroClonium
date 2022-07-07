.class Lconst_test;
.super Ljava/lang/Object;

.method public static const4_test()V
    .locals 1

    const/4 v0, -0x3

    return-void
.end method

.method public static const16_test()V
    .locals 1

    const/16 v0, 0x4242

    return-void
.end method

.method public static const16Negative_test()V
    .locals 1

    const/16 v0, -0xf

    return-void
.end method

.method public static const_test()V
    .locals 1

    const v0, 0x42424242

    return-void
.end method


.method public static constHigh16_test()V
    .locals 1

    const/high16 v0, 0x42420000

    return-void
.end method

.method public static constWide16_test()V
    .locals 2

    const-wide/16 v0, 0x4242

    return-void
.end method

.method public static constWide32_test()V
    .locals 2

    const-wide/32 v0, 0x1b7740

    return-void
.end method

.method public static constWide_test()V
    .locals 2

    const-wide v0, 0x4242424242424242L

    return-void
.end method

.method public static constWideHigh16_test()V
    .locals 2

    const-wide/high16 v0, 0x4242000000000000L

    return-void
.end method

.method public static constString_test()V
    .locals 1

    const-string v0, "When I need to identify rebels, I look for men with principles."

    return-void
.end method

.method public static constString_UTF_test()V
    .locals 1

    const-string v0, "\u0633\u0644\u0627\u0645"

    return-void
.end method


.method public static constStringJumbo_test()V
    .locals 1

    const-string/jumbo v0, "When I need to identify JUMBO rebels, I look for JUMBO men with JUMBO principles."

    return-void
.end method

.method public static constClassLocal_test()V
    .locals 1

    const-class v0, Lconst_test;

    return-void
.end method

.method public static constClassRemote_test()V
    .locals 1

    const-class v0, Ljava/util/HashMap;

    return-void
.end method

.method public static constClassPrimitive_test()V
    .locals 1

    const-class v0, I

    return-void
.end method

.method public static constClassBadClass_test()Ljava/lang/Class;
    .locals 1

    const-class v0, LBadClass;

    return-object v0
.end method

.method public static constClassUnknown_test()V
    .locals 1

    const-class v0, Lunknown/class;

    return-void
.end method
