.class public LAmbgValTest;
.super Ljava/lang/Object;


.method public constructor <init>()V
    .locals 0

    .line 6
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


.method public static moveAmbiguousValueTest(I)I
    .locals 1

    move v0, p0

    return v0
.end method

.method public static moveWideAmbiguousValueTest(D)D
    .locals 1

    move-wide v0, p0

    return v0
.end method

.method public static moveObjectAmbiguousValueTest(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1

    move-object v0, p0

    return v0
.end method


.method public static testNewArray(I)[Ljava/lang/Object;
    .locals 1
    new-array v0, p0, [Ljava/lang/Object;

    return-object v0
.end method

.method public static testArrayLength([Ljava/lang/Object;)I
    .locals 1

    array-length v0, p0

    return v0
.end method

.method public static testCmpKind(FF)I
    .locals 1
    cmpg-float v0, p0, p1

    return v0
.end method

.method public static testUnaryOp1(I)I
    .locals 1
    neg-int v0, p0

    return v0
.end method

.method public static testUnaryOp2(I)C
    .locals 1
    int-to-char v0, p0

    return v0
.end method

.method public static testUnaryOp3(F)J
    .locals 1
    float-to-long v0, p0

    return v0
.end method

.method public static testUnaryOp4(D)D
    .locals 1
    neg-double v0, p0

    return v0
.end method

.method public static testCheckCast(Ljava/lang/Object;)V
    .locals 1

    check-cast p0, Ljava/util/List;

    return-void
.end method

.method public static testIfTest(I)V
    .locals 1

    const/4 v0, -0x3

    if-eq v0, p0, :end

    nop

    nop

    :end
    return-void
.end method

.method public static testIfTestZ(I)V
    .locals 1

    if-eqz p0, :end

    nop

    nop

    :end
    return-void
.end method

.method public static testInstanceOf(Ljava/lang/Object;)I
    .locals 1

    instance-of v0, p0, Ljava/util/List;

    if-eqz v0, :end

    nop

    :end
    return v0
.end method

.method public static testPackedSwitch(I)Ljava/lang/String;
    .locals 1

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

.method public static testSparseSwitch(I)I
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

    return-void
.end method

.method public static testBinOp1(I)I
    .locals 1

    const v0, 0xa1

    div-int v0, v0, p0

    return v0
.end method

.method public static testBinOp2(I)I
    .locals 1

    const v0, 0xF

    and-int/2addr v0, p0

    return v0
.end method

.method public static testBinOp3(I)I
    .locals 1

    rsub-int v0, p0, 0xff

    return-void
.end method

.method public static testBinOp4(I)I
    .locals 1

    ushr-int/lit8 v0, p0, 0x2

    return v0
.end method

.method public static testAGet_Ambg_index(I)I
    .locals 1

    const v0, 0x5

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    aget v0, v0, p0

    return v0

    :array_0
    .array-data 4
        0x1
        0x2
        0x3
        0x4
        0x5
    .end array-data
.end method

.method public static testAGet_Ambg_array([I)I
    .locals 1

    const/4 v0, 0x0

    aget v0, p0, v0

    return v0
.end method

.method public static testAPut_Ambg_array([I)V
    .locals 1

    const/4 v0, 0x0

    aput v0, p0, v0

    return-void
.end method

.method public static testAPut_Ambg_index(I)[I
    .locals 2

    const v0, 0x5

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    const v1, 0xa

    aput v1, v0, p0

    return-object v0

    :array_0
    .array-data 4
        0x1
        0x2
        0x3
        0x4
        0x5
    .end array-data
.end method

.method public static testAPut_Ambg_value(I)[I
    .locals 2

    const v0, 0x5

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    const v1, 0x1

    aput p0, v0, v1

    return-object v0

    :array_0
    .array-data 4
        0x1
        0x2
        0x3
        0x4
        0x5
    .end array-data

    return-void
.end method

.method public static testSPutAmbgValue(ILjava/lang/Object;)V
    .locals 0

    sput p0, LFieldOwner;->staticIntField:I

    sput p1, LFieldOwner;->staticObjectField:Ljava/lang/Object;

    return-void
.end method

.method public static testSGetInt()I
    .locals 1

    sget v0, LFieldOwner;->staticIntField:I

    return v0
.end method

.method public static testSGetObject()Ljava/lang/Object;
    .locals 1

    sget v0, LFieldOwner;->staticObjectField:Ljava/lang/Object;

    return-object v0
.end method

.method public static testIPutAmbiguousReferenceObject(LFieldOwner;)V
    .locals 1

    const/4 v0, 0x1

    iput v0, p0, LFieldOwner;->instanceIntField:I

    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    iput v0, p0, LFieldOwner;->instanceObjectField:Ljava/lang/Object;

    return-void
.end method

.method public static testIGetAmbiguousReferenceObject1(LFieldOwner;)I
    .locals 1

    iget v0, p0, LFieldOwner;->instanceIntField:I

    return v0
.end method

.method public static testIGetAmbiguousReferenceObject2(LFieldOwner;)Ljava/lang/Object;
    .locals 1

    iget-object v0, p0, LFieldOwner;->instanceObjectField:Ljava/lang/Object;

    return-object v0
.end method

.method public static testIPutAmbiguousValue(I)LFieldOwner;
    .locals 1

    new-instance v0, LFieldOwner;

    invoke-direct {v0}, LFieldOwner;-><init>()V

    iput p0, v0, LFieldOwner;->instanceIntField:I

    return-object v0
.end method

.method public static testIPutAmbiguousValue(Ljava/lang/Object;)LFieldOwner;
    .locals 1

    new-instance v0, LFieldOwner;

    invoke-direct {v0}, LFieldOwner;-><init>()V

    iput-object p0, v0, LFieldOwner;->instanceObjectField:Ljava/lang/Object;

    return-object v0
.end method

.method public static testIGetAmbiguousValue1(LFieldOwner;)I
    .locals 1

    iget v0, p0, LFieldOwner;->instanceIntField:I

    return v0
.end method

.method public static testIGetAmbiguousValue2(LFieldOwner;)Ljava/lang/Object;
    .locals 1

    iget v0, p0, LFieldOwner;->instanceObjectField:Ljava/lang/Object;

    return-object v0
.end method

.method public static testInvokeStaticOnSmaliMethodWithAmbiguousParameter(I)I
    .locals 1
    invoke-static {p0}, LMethodOwner;->staticMethod1(I)I

    move-result v0

    return v0
.end method

.method public static testInvokeStaticOnJavaMethodWithAmbiguousParameter([Ljava/lang/Integer;)Z
    .locals 1

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    invoke-static {v0, p0}, Ljava/util/Collections;->addAll(Ljava/util/Collection;[Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public static testInvokeVirtualWithAmbiguousParamOnSmaliMethod(I)I
    .locals 2

    new-instance v0, LMethodOwner;

    invoke-direct {v0}, LMethodOwner;-><init>()V

    invoke-virtual {v0, p0}, LMethodOwner;->instanceMethod(I)I

    move-result v1

    return v1
.end method

.method public static testInvokeVirtualWithAmbiguousParamOnJavaMethod(Ljava/lang/Object;)Z
    .locals 2

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    invoke-virtual {v0, p0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    move-result v1

    return v1
.end method


.method public static testInvokeVirtualWithAmbiguousParamAndRef(Ljava/lang/Object;)Ljava/lang/String;
    .locals 2

   new-instance v0, LJavaDescendentClass;

   invoke-direct {v0}, LJavaDescendentClass;-><init>()V

   invoke-virtual {v0, p0}, LJavaDescendentClass;->addToListAndSaySize(Ljava/lang/Object;)Ljava/lang/String;

   move-result-object v1

   return-object v1
.end method

.method public static testConstructorInvokeDirectOnJavaObject(I)Ljava/util/ArrayList;
    .locals 1

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0, p0}, Ljava/util/ArrayList;-><init>(I)V

    return-object v0
.end method


.method public static testConstructorInvokeDirectOnSmaliObjekt1(I)LMethodOwner;
    .locals 1
    new-instance v0, LMethodOwner;

    invoke-direct {v0, p0}, LMethodOwner;-><init>(I)V

    return-object v0
.end method

.method public static testConstructorInvokeDirectOnSmaliObjekt2(I)LJavaDescendentClass;
    .locals 1
    new-instance v0, LJavaDescendentClass;

    invoke-direct {v0, p0}, LJavaDescendentClass;-><init>(I)V

    return-object v0
.end method

.method public static testPrivateMethodInvokeDirect(I)I
.locals 1

    new-instance v0, LMethodOwner;

    invoke-direct {v0}, LMethodOwner;-><init>()V

    invoke-virtual {v0, p0}, LMethodOwner;->wrapperOnPrivateInstanceMethod(I)I

    move-result v0

    return v0
.end method


.method public static testPrivateMethodInvokeDirect(LMethodOwner;)I
    .locals 1

    const/4 v0, 0x4

    invoke-virtual {p0, v0}, LMethodOwner;->wrapperOnPrivateInstanceMethod(I)I

    move-result v0

    return v0
.end method

.method public static testInnerBranchingInvokeStatic(I)Z
    .locals 1

    invoke-static {p0}, LMethodOwner;->isIntZero(I)Z

    move-result v0

    return v0
.end method

.method public static testInnerBranchingInvokeVirtual(I)Z
    .locals 1

    new-instance v0, LMethodOwner;

    invoke-direct {v0}, LMethodOwner;-><init>()V

    invoke-virtual {v0, p0}, LMethodOwner;->isIntOne(I)Z

    move-result v0

    return v0
.end method

.method public static testInnerBranchingInvokeDirectPrivateMethod(I)Z
    .locals 1

    new-instance v0, LAmbgValTest;

    invoke-direct {v0}, LAmbgValTest;-><init>()V

    invoke-direct {v0, p0}, LAmbgValTest;->isIntTwo(I)Z

    move-result v0

    return v0
.end method

.method private isIntTwo(I)Z
    .locals 2

    const/4 v0, 0x2

    if-eq p1, v0, :end

    const/4 v0, 0x0

    return v0

    :end
    nop

    const/4 v0, 0x1

    return v0
.end method

.method public static testInnerBranchingInvokeSuper(I)Z
    .locals 1

    new-instance v0, LSmaliDescendentClass;

    invoke-direct {v0}, LSmaliDescendentClass;-><init>()V

    invoke-virtual {v0, p0}, LSmaliDescendentClass;->isIntNegative(I)Z

    move-result v0

    return v0
.end method

#.method public static testInnerBranchingInvokeSuper(I)Z
#    .locals 1
#
#    //TODO
#
#    invoke-super {p0}, LMethodOwner;->isIntZero(I)Z
#
#    move-result v0
#
#    return v0
#.end method

#.method public static testInnerBranchingInvokeDirectSmaliConstructor(I)Z
#    .locals 1
#
#    //TODO
#
#    invoke-super {p0}, LMethodOwner;->isIntZero(I)Z
#
#    move-result v0
#
#    return v0
#.end method


.method public static testThrow(Ljava/lang/Exception;)V
    .locals 0

    throw p0

    nop

    return-void
.end method


.method public static testThrowWrapper(Ljava/lang/Exception;)I
    .locals 1

    :try_start_0

    invoke-static {p0}, LAmbgValTest;->testThrow(Ljava/lang/Exception;)V

    const/4 v0, 0x1

    return v0

    :try_end_0
    .catch Ljava/lang/NullPointerException; {:try_start_0 .. :try_end_0} :catch_0

    nop

    :catch_0

    const/4 v0, 0x2

    return v0

.end method