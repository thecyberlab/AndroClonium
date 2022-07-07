.class public LFatEnumTest;
.super Ljava/lang/Object;
.source "FatEnumTest.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static test1()V
    .locals 3

    .line 8
    sget-object v0, LFatEnum;->STATE1:LFatEnum;

    .line 9
    .local v0, "fe1":LFatEnum;
    sget-object v1, LFatEnum;->STATE2:LFatEnum;

    .line 10
    .local v1, "fe2":LFatEnum;
    sget-object v2, LFatEnum;->STATE3:LFatEnum;

    .line 11
    .local v2, "fe3":LFatEnum;
    return-void
.end method

.method public static test2()I
    .locals 1

    .line 14
    sget-object v0, LFatEnum;->STATE1:LFatEnum;

    iget v0, v0, LFatEnum;->a:I

    return v0
.end method

.method public static test3()[I
    .locals 1

    .line 18
    sget-object v0, LFatEnum;->STATE1:LFatEnum;

    iget-object v0, v0, LFatEnum;->aa:[I

    return-object v0
.end method

.method public static test4()Ljava/lang/String;
    .locals 1

    .line 22
    sget-object v0, LFatEnum;->STATE1:LFatEnum;

    iget-object v0, v0, LFatEnum;->name:Ljava/lang/String;

    return-object v0
.end method

.method public static test5()I
    .locals 1

    .line 26
    sget-object v0, LFatEnum;->STATE1:LFatEnum;

    iget v0, v0, LFatEnum;->ordinal:I

    return v0
.end method

.method public static test6()V
    .locals 1

    .line 30
    sget-object v0, LFatEnum;->STATE1:LFatEnum;

    invoke-virtual {v0}, LFatEnum;->methodInEnumTest1()V

    .line 31
    return-void
.end method

.method public static test7()I
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 34
    sget-object v0, LFatEnum;->STATE1:LFatEnum;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, LFatEnum;->read(Ljava/nio/CharBuffer;)I

    move-result v0

    return v0
.end method
