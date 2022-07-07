.class public LSmaliDescendentClass;
.super LJavaDescendentClass;


.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, LJavaDescendentClass;-><init>()V

    return-void
.end method

.method public isIntNegative(I)Z
    .locals 1

    invoke-super {p0, p1}, LJavaDescendentClass;->isIntNegative(I)Z

    move-result v0

    return v0
.end method