.class public final enum LEnumForInvokeStaticTest;
.super Ljava/lang/Enum;
.source "EnumForInvokeStaticTest.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "LEnumForInvokeStaticTest;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[LEnumForInvokeStaticTest;

.field public static final enum STATE1:LEnumForInvokeStaticTest;

.field public static final enum STATE2:LEnumForInvokeStaticTest;


# direct methods
.method static constructor <clinit>()V
    .locals 5

    .line 4
    new-instance v0, LEnumForInvokeStaticTest;

    const/4 v1, 0x0

    const-string v2, "STATE1"

    invoke-direct {v0, v2, v1}, LEnumForInvokeStaticTest;-><init>(Ljava/lang/String;I)V

    sput-object v0, LEnumForInvokeStaticTest;->STATE1:LEnumForInvokeStaticTest;

    .line 5
    new-instance v0, LEnumForInvokeStaticTest;

    const/4 v2, 0x1

    const-string v3, "STATE2"

    invoke-direct {v0, v3, v2}, LEnumForInvokeStaticTest;-><init>(Ljava/lang/String;I)V

    sput-object v0, LEnumForInvokeStaticTest;->STATE2:LEnumForInvokeStaticTest;

    .line 3
    const/4 v3, 0x2

    new-array v3, v3, [LEnumForInvokeStaticTest;

    sget-object v4, LEnumForInvokeStaticTest;->STATE1:LEnumForInvokeStaticTest;

    aput-object v4, v3, v1

    aput-object v0, v3, v2

    sput-object v3, LEnumForInvokeStaticTest;->$VALUES:[LEnumForInvokeStaticTest;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 3
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static staticMethodFromEnum()I
    .locals 1

    .line 8
    const/16 v0, 0xa

    return v0
.end method

.method public static valueOf(Ljava/lang/String;)LEnumForInvokeStaticTest;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .line 3
    const-class v0, LEnumForInvokeStaticTest;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LEnumForInvokeStaticTest;

    return-object v0
.end method

.method public static values()[LEnumForInvokeStaticTest;
    .locals 1

    .line 3
    sget-object v0, LEnumForInvokeStaticTest;->$VALUES:[LEnumForInvokeStaticTest;

    invoke-virtual {v0}, [LEnumForInvokeStaticTest;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LEnumForInvokeStaticTest;

    return-object v0
.end method
