.class public LSimpleInstructions;
.super Ljava/lang/Object;


.method public static test1()I
    .locals 3

    const/4 v0, 0x4 #0

    const-string v1, "Hell" #1

    new-array v2, v0, [Ljava/lang/Object; #2

    array-length v1, v2 #3

    const v2, 0x64 #4

    if-eq v0, v1, :end #5

    const v2, 0xF2 #6

    :end
    return v2 #7
.end method


.method public static test2()I
    .locals 2

    const/4 v0, 0x4

    const/4 v1, 0x0

    div-int/2addr v0, v1

    const/4 v1, 0x1

    add-int/2addr v0, v1

    :end
    return v0
.end method
