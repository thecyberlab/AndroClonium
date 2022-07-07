.class Lthrow_test;
.super Ljava/lang/Object;

.method public static throwTest()V
  .locals 1

  throw v0
.end method

.method public static throwCatchTest()Ljava/lang/Object;
    .locals 1

    :try_start_0

    throw v0

    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :catch_0
    move-exception v0

    return-object v0
.end method