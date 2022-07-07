.class Linstanceof_test;
.super Ljava/lang/Object;

.method public static instanceOfObject()Z
    .locals 2

    instance-of v1, v0, Ljava/lang/Object;

    return v1
.end method


.method public static instanceOfString()Z
    .locals 2

    instance-of v1, v0, Ljava/lang/String;

    return v1
.end method



.method public static instanceOfStringArray()Z
    .locals 2

    instance-of v1, v0, [Ljava/lang/String;

    return v1
.end method

.method public static instanceOfObjectArray()Z
    .locals 2

    instance-of v1, v0, [Ljava/lang/Object;

    return v1
.end method


.method public static instanceOfIntArray()Z
    .locals 2

    instance-of v1, v0, [I

    return v1
.end method


.method public static instanceOf2DSelfArray()Z
  .locals 2

  instance-of v1, v0, [[Linstanceof_test;

  return v1
.end method

.method public static instanceOf2DObjectArray()Z
  .locals 2

  instance-of v1, v0, [[Ljava/lang/Object;

  return v1
.end method
