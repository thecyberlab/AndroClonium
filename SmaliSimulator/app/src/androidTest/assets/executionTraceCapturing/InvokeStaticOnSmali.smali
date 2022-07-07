.class public LInvokeStaticOnSmali;
.super Ljava/lang/Object;


.method public static test1()I
    .locals 2

    const v0, 0xa

    invoke-static {v0}, LInvokeStaticOnSmali;->t1(I)I

    move-result v0

    return v0
.end method


.method public static t1(I)I
    .locals 1

    const/4 v0, 0x5

    add-int/2addr v0, p0

    return v0
.end method

.method public static test2()I
    .locals 2

    const v0, 0x4

    const v1, 0x0

    invoke-static {v0, v1}, LInvokeStaticOnSmali;->t2(II)I

    move-result v0

    return v0
.end method


.method public static t2(II)I
    .locals 1

    if-eqz p0, :end

    add-int/2addr p1, p0

    const/4 v0, 0x1

    sub-int/2addr p0, v0

    invoke-static {p0, p1}, LInvokeStaticOnSmali;->t2(II)I

    move-result v0

    return v0

    :end
    return p1
.end method