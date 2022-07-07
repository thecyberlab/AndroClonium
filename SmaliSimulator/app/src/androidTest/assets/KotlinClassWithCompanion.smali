.class public final LKotlinClassWithCompanion;
.super Ljava/lang/Object;
.source "KotlinClassWithCompanion.kt"


# static fields
.field public static final Companion:LKotlinClassWithCompanion$Companion;

.field private static final test:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    new-instance v0, LKotlinClassWithCompanion$Companion;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, LKotlinClassWithCompanion$Companion;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    sput-object v0, LKotlinClassWithCompanion;->Companion:LKotlinClassWithCompanion$Companion;

    const-string v0, "Hello"

    sput-object v0, LKotlinClassWithCompanion;->test:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static final synthetic access$getTest$cp()Ljava/lang/String;
    .locals 1

    sget-object v0, LKotlinClassWithCompanion;->test:Ljava/lang/String;

    return-object v0
.end method
