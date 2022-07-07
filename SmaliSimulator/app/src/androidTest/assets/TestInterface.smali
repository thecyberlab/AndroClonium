.class public interface abstract LTestInterface;
.super Ljava/lang/Object;
.source "TestInterface.java"


# static fields
.field public static final ii1:I = 0x3

.field public static final ii2:I = 0x0

.field public static final ss1:Ljava/lang/String; = "hello from static interface"

.field public static final ss2:Ljava/lang/String; = "hello from instance interface"

.field public static final ss3:Ljava/lang/String;

.field public static final ss4:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 8
    const-string v0, "Hello from interface clinit 1"

    sput-object v0, LTestInterface;->ss3:Ljava/lang/String;

    const-string v0, "Hello from interface clinit 2"

    .line 9
    sput-object v0, LTestInterface;->ss4:Ljava/lang/String;

    return-void
.end method


# virtual methods
.method public abstract test()V
.end method
