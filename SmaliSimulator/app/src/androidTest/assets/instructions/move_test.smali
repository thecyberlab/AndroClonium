.class Lmove_test;
.super Ljava/lang/Object;

.method public static moveTest1()V
    .locals 1

    const/4 v0, 0x1

    move v1, v0

    return-void
.end method

.method public static moveTest2()V
    .locals 2

    const-wide/32 v0, 0x1b7740

    move-wide v1, v0

    return-void
.end method

.method public static moveTest3()V
    .locals 2

    new-instance v0, Ljava/lang/Object;

    move-object v1, v0

    return-void
.end method

.method public static moveTest4()V
    .locals 2

    const-string v0, "text1"

    const-string v1, "text2"

    filled-new-array {v0, v1}, [Ljava/lang/String;

    move-result-object v0

    return-void
.end method


.method public static moveTest5()V
    .locals 2

    const/16 v17, 0xFF

    move/from16 v18, v17

    return-void
.end method

.method public static moveTest6()V
    .locals 2

    const v25, 0xFFFFFFFF

    move/16 v256, v25

    return-void

.end method

#.method public static moveTest7()V
#
#.end method
#
#.method public static moveTest8()V
#
#.end method

