.class public LcyclicEnum2/EnumWithCyclicConstructorCompanion;
.super Ljava/lang/Object;
.source "EnumWithCyclicConstructorCompanion.java"


# static fields
.field public static state1:LcyclicEnum2/EnumWithCyclicConstructor;

.field public static state2:LcyclicEnum2/EnumWithCyclicConstructor;

.field public static state3:LcyclicEnum2/EnumWithCyclicConstructor;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    sget-object v0, LcyclicEnum2/EnumWithCyclicConstructor;->State1:LcyclicEnum2/EnumWithCyclicConstructor;

    sput-object v0, LcyclicEnum2/EnumWithCyclicConstructorCompanion;->state1:LcyclicEnum2/EnumWithCyclicConstructor;

    sget-object v0, LcyclicEnum2/EnumWithCyclicConstructor;->State2:LcyclicEnum2/EnumWithCyclicConstructor;

    sput-object v0, LcyclicEnum2/EnumWithCyclicConstructorCompanion;->state2:LcyclicEnum2/EnumWithCyclicConstructor;

    sget-object v0, LcyclicEnum2/EnumWithCyclicConstructor;->State3:LcyclicEnum2/EnumWithCyclicConstructor;

    sput-object v0, LcyclicEnum2/EnumWithCyclicConstructorCompanion;->state3:LcyclicEnum2/EnumWithCyclicConstructor;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
