.class public interface abstract LTestInterface;
.super Ljava/lang/Object;
.source "TestInterface.java"


# virtual methods
.method public abstract testInterfaceMethod()I
.end method

.method public abstract throwException()V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation
.end method
