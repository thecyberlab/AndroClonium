.class public Lswitch_test;
.super Ljava/lang/Object;


.method public static packedSwitchTest(C)Ljava/lang/String;
    .locals 1
    .param p0, "grade"    # C

    packed-switch p0, :pswitch_data_0

    :pswitch_0
    const-string v0, "Invalid grade"

    goto :goto_0

    :pswitch_1
    nop

    :pswitch_2
    const-string v0, "Better try again"

    goto :goto_0

    :pswitch_3
    const-string v0, "Well done"

    goto :goto_0

    :pswitch_4
    const-string v0, "Excellent!"

    nop

    :goto_0
    return-object v0

    nop

    :pswitch_data_0
    .packed-switch 0x41
        :pswitch_4
        :pswitch_3
        :pswitch_3
        :pswitch_1
        :pswitch_0
        :pswitch_2
    .end packed-switch
.end method



.method public static sparseSwitch(I)I
    .locals 1

    const v0, 0xa

    sparse-switch p0, :sswitch_data_0

    return v0

    :sswitch_0

    const v0, 0x14

    return v0

    :sswitch_1

    const v0, 0x1e

    return v0

    nop

    :sswitch_data_0
    .sparse-switch
        0x1 -> :sswitch_0
        0x2 -> :sswitch_1
    .end sparse-switch
.end method
