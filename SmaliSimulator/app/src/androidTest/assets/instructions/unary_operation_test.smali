.class Lunary_operation_test;
.super Ljava/lang/Object;

.method public static negInt()V
    .locals 1

    const/4 v0, 0x6

    neg-int v0, v0

    return-void
.end method

.method public static notInt()V
    .locals 1

    const/4 v0, 0x6

    not-int v0, v0

    return-void
.end method

.method public static negLong()V
    .locals 2

    const-wide/32 v0,0xff00

    neg-long v0, v0

    return-void
.end method

.method public static notLong()V
    .locals 2

    const-wide/32 v0,0xff00

    not-long v0, v0

    return-void
.end method

.method public static negFloat()V
    .locals 2

    const/high16 v0, 0x40200000    # 2.5f

    neg-float v0, v0

    return-void
.end method

.method public static negDouble()V
    .locals 2


    const-wide v0, 0x401400001421f5f4L    # 5.0000003

    neg-double v0, v0

    return-void
.end method

.method public static intToLong()V
    .locals 2

    const v0,0xff00

    const v1,0xff00

    int-to-long v0, v0

    return-void
.end method

.method public static intToFloat()V
    .locals 2

    const v0, 0x40200000

    int-to-float v2, v0

    return-void
.end method

.method public static intToDouble()V
    .locals 2

    const v0, 0x40200000

    int-to-double v2, v0

    return-void
.end method

.method public static longToInt()V
    .locals 2

    const-wide v0, 0x00000000000F0000

    long-to-int v0, v0

    return-void
.end method

.method public static longToFloat()V
    .locals 2

    const-wide v0, 0x0000000040200000

    long-to-float v0, v0

    return-void
.end method

.method public static longToDouble()V
    .locals 2

    const-wide v0, 0x0000000040200000

    long-to-double v0, v0

    return-void
.end method

.method public static floatToInt()V
    .locals 2

    const v0, 0x40200000

    float-to-int v0, v0

    return-void
.end method

.method public static floatToLong()V
    .locals 2

    const v0, 0x40200000

    float-to-long v0, v0

    return-void
.end method

.method public static floatToDouble()V
    .locals 2

    const v0, 0x40200000

    float-to-double v0, v0

    return-void
.end method

.method public static doubleToInt()V
    .locals 2

    const-wide v0, 0x401400001421f5f4L    # 5.0000003

    double-to-int v0, v0

    return-void
.end method

.method public static doubleToLong()V
    .locals 2

    const-wide v0, 0x401400001421f5f4L    # 5.0000003

    double-to-long v0, v0

    return-void
.end method

.method public static doubleToFloat()V
    .locals 2

    const-wide v0, 0x401400001421f5f4L

    double-to-float v0, v0

    return-void
.end method

.method public static intToByte()V
    .locals 1

    const/16 v0,0x401

    int-to-byte v0, v0

    return-void
.end method

.method public static intToChar()V
    .locals 1

    const v0,-0x1

    int-to-char v0, v0

    return-void
.end method

.method public static intToShort()V
    .locals 2

    const v0,0xFFFF

    const v1,0xFFFF

    int-to-short v0, v0

    return-void
.end method
