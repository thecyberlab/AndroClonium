.class Lif_test_and_if_testZ;
.super Ljava/lang/Object;

.method public static ifEqual1()V
    .locals 2

    const/4 v0, 0x3

    if-eq v0, v1, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifEqual2()V
    .locals 2

    const/4 v0, 0x3

    const/4 v1, 0x3

    if-eq v0, v1, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifNotEqual1()V
    .locals 2

    const/4 v0, 0x3

    const/4 v1, 0x3

    if-ne v0, v1, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifNotEqual2()V
    .locals 2

    const/4 v1, 0x3

    if-ne v0, v1, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifLessThan1()V
    .locals 2

    if-lt v0, v1, :end

    const/4 v0, 0x5

    :end
    return-void
.end method

.method public static ifLessThan2()V
    .locals 2

    const/4 v0, 0x4

    const/4 v1, 0x3

    if-lt v0, v1, :end

    const/4 v0, 0x5

    :end
    return-void
.end method

.method public static ifLessThan3()V
    .locals 2

    const/4 v0, 0x3

    const/4 v1, 0x4

    if-lt v0, v1, :end

    const/4 v0, 0x5

    :end
    return-void
.end method

.method public static ifLessOrEqual1()V
    .locals 2

    if-le v0, v1, :end

    const/4 v0, 0x5

    :end
    return-void
.end method


.method public static ifLessOrEqual2()V
    .locals 2

    const/4 v0, 0x4

    const/4 v1, 0x3

    if-le v0, v1, :end

    const/4 v0, 0x5

    :end
    return-void
.end method


.method public static ifLessOrEqual3()V
    .locals 2

    const/4 v0, 0x3

    const/4 v1, 0x4

    if-le v0, v1, :end

    const/4 v0, 0x5

    :end
    return-void
.end method

.method public static ifGreaterOrEqual1()V
    .locals 2

    if-ge v0, v1, :end

    const/4 v0, 0x7

    :end
    return-void
.end method

.method public static ifGreaterOrEqual2()V
    .locals 2

    const/4 v1, -0x1

    if-ge v0, v1, :end

    const/4 v0, 0x7

    :end
    return-void
.end method

.method public static ifGreaterOrEqual3()V
    .locals 2

    const/4 v1, 0x1

    if-ge v0, v1, :end

    const/4 v0, 0x7

    :end
    return-void
.end method


.method public static ifGreaterThan1()V
    .locals 2

    if-gt v0, v1, :end

    const/4 v0, 0x7

    :end
    return-void
.end method

.method public static ifGreaterThan2()V
    .locals 2

    const/4 v1, -0x7

    if-gt v0, v1, :end

    const/4 v0, 0x7

    :end
    return-void
.end method

.method public static ifGreaterThan3()V
    .locals 2

    const/4 v0, 0x3

    if-gt v0, v1, :end

    const/4 v0, 0x7

    :end
    return-void
.end method
#----------------------------------------------------------

.method public static ifEqualZero1()V
    .locals 1

    if-eqz v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method


.method public static ifEqualZero2()V
    .locals 1

    const/4 v0, 0x3

    if-eqz v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method


.method public static ifNotEqualZero1()V
    .locals 1

    if-nez v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifNotEqualZero2()V
    .locals 1

    const/4 v0, 0x3

    if-nez v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifLessThanZero1()V
    .locals 1

    if-ltz v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifLessThanZero2()V
    .locals 1

    const/4 v0, 0x2

    if-ltz v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifLessThanZero3()V
    .locals 1

    const/4 v0, -0x4

    if-ltz v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method


.method public static ifGreaterOrEqualZero1()V
    .locals 1

    if-gez v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifGreaterOrEqualZero2()V
    .locals 1

    const/4 v0, 0x3

    if-gez v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifGreaterOrEqualZero3()V
    .locals 1

    const/4 v0, -0x4

    if-gez v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method


.method public static ifGreaterThanZero1()V
    .locals 1

    if-gtz v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifGreaterThanZero2()V
    .locals 1

    const/4 v0, 0x3

    if-gtz v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifGreaterThanZero3()V
    .locals 1

    const/4 v0, -0x3

    if-gtz v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method


.method public static ifLessOrEqualZero1()V
    .locals 1

    if-lez v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifLessOrEqualZero2()V
    .locals 1

    const/4 v0, 0x3

    if-lez v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

.method public static ifLessOrEqualZero3()V
    .locals 1

    const/4 v0, -0x2

    if-lez v0, :end

    const/4 v0, 0x4

    :end
    return-void
.end method

#----------------------------------------------------------
.method public static notNullCheckObject()I
    .locals 2

    #new-instance v0, Ljava/lang/Object;

    #invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    if-nez v0, :cond_0

    const/4 v1, 0x0

    return v1

    :cond_0
    const/4 v1, 0x1

    return v1
.end method


.method public static nullCheckObject()I
    .locals 2

    #new-instance v0, Ljava/lang/Object;

    #invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    return v1

    :cond_0
    const/4 v1, 0x1

    return v1
.end method
