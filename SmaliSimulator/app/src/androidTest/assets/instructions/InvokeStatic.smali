.class public LInvokeStatic;
.super Ljava/lang/Object;
.source "InvokeStatic.java"


# static fields
.field static testField:I = 0x0


.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static returnConstantTest()I
   .locals 1

   invoke-static {}, LInvokeStatic;->returnConstant()I

   move-result v0

   return v0
.end method

.method public static returnConstant()I
   .locals 1

   const/4 v0, 0x3

   return v0
.end method


.method public static IntegerParse(Ljava/lang/String;)I
    .locals 1
    .param p0, "s"    # Ljava/lang/String;

    .line 16
    invoke-static {p0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method public static IntegerParse2(Ljava/lang/String;)V
    .locals 1
    .param p0, "s"    # Ljava/lang/String;

    .line 20
    invoke-static {p0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    sput v0, LInvokeStatic;->testField:I

    .line 21
    return-void
.end method

.method public static adder_(II)I
    .locals 1
    .param p0, "a"    # I
    .param p1, "b"    # I

    .line 8
    add-int v0, p0, p1

    return v0
.end method


.method public static adder(II)I
    .locals 1
    .param p0, "a"    # I
    .param p1, "b"    # I

    invoke-static {p0,p1}, LInvokeStatic;->adder_(II)I

    move-result v0

    return v0
.end method



.method public static adder2_(II)V
    .locals 1
    .param p0, "a"    # I
    .param p1, "b"    # I

    .line 12
    add-int v0, p0, p1

    sput v0, LInvokeStatic;->testField:I

    .line 13
    return-void
.end method

.method public static adder2(II)V
    .locals 1
    .param p0, "a"    # I
    .param p1, "b"    # I

    invoke-static {p0, p1}, LInvokeStatic;->adder2_(II)V

    return-void
.end method

.method public static exceptionTest1()V
    .locals 0

    .line 28
    invoke-static {}, LInvokeStatic;->throwException1()V

    .line 29
    return-void
.end method

.method public static exceptionTest2()V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 32
    const-string v0, "z"

    invoke-static {v0}, LInvokeStatic;->IntegerParse(Ljava/lang/String;)I

    .line 33
    return-void
.end method



.method public static throwException1()V
    .locals 2

    const/4 v0, 0x3

    const/4 v1, 0x0

    div-int/2addr v0, v1

    return-void
.end method

.method public static classForNameTest()Ljava/lang/Object;
    .locals 1

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    return-object v0
.end method

.method public static enumValueOfTest()Ljava/lang/Object;
    .locals 1

    .line 52
    const-string v0, "READ"

    invoke-static {v0}, Ljava/nio/file/AccessMode;->valueOf(Ljava/lang/String;)Ljava/nio/file/AccessMode;

    move-result-object v0

    return-object v0
.end method

.method public static enumValueOfTest2()Ljava/lang/Object;
    .locals 2

    .line 57
    const-class v0, Ljava/nio/file/AccessMode;

    const-string v1, "READ"

    invoke-static {v0, v1}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    return-object v0
.end method

.method public static enumValuesTest()Ljava/lang/Object;
    .locals 1

    .line 48
    invoke-static {}, Ljava/nio/file/AccessMode;->values()[Ljava/nio/file/AccessMode;

    move-result-object v0

    return-object v0
.end method

.method public static staticMethodInEnumCall()I
    .locals 1

    .line 70
    invoke-static {}, LEnumForInvokeStaticTest;->staticMethodFromEnum()I

    move-result v0

    return v0
.end method

.method public static enumValueOfTest3()Ljava/lang/Object;
    .locals 2

    .line 66
    const-class v0, LEnumForInvokeStaticTest;

    const-string v1, "STATE1"

    invoke-static {v0, v1}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    return-object v0
.end method

.method public static getEnumClass()Ljava/lang/Object;
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/ClassNotFoundException;
        }
    .end annotation

    .line 74
    const-string v0, "EnumForInvokeStaticTest"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    return-object v0
.end method

.method public static addMultiple(JIJI)J
    .locals 4
    .param p0, "l1"    # J
    .param p2, "i1"    # I
    .param p3, "l2"    # J
    .param p5, "i2"    # I

    int-to-long v0, p2

    add-long/2addr v0, p0

    add-long/2addr v0, p3

    int-to-long v2, p5

    add-long/2addr v0, v2

    return-wide v0
.end method

.method public static pairRegisterArgPassingTest1()J
    .locals 6

    .line 82
    const-wide/16 v0, 0xa

    const/16 v2, 0xb

    const-wide/16 v3, 0xc

    const/16 v5, 0xd

    invoke-static/range {v0 .. v5}, LInvokeStatic;->addMultiple(JIJI)J

    move-result-wide v0

    return-wide v0
.end method