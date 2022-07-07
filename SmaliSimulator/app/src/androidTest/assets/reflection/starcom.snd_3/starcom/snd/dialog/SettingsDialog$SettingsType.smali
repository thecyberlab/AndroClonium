.class public final enum Lstarcom/snd/dialog/SettingsDialog$SettingsType;
.super Ljava/lang/Enum;
.source "SettingsDialog.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lstarcom/snd/dialog/SettingsDialog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "SettingsType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum",
        "<",
        "Lstarcom/snd/dialog/SettingsDialog$SettingsType;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lstarcom/snd/dialog/SettingsDialog$SettingsType;

.field public static final enum CustomChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

.field public static final enum DefaultChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

.field public static final enum EditChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

.field public static final enum Main:Lstarcom/snd/dialog/SettingsDialog$SettingsType;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .prologue
    const/4 v5, 0x3

    const/4 v4, 0x2

    const/4 v3, 0x1

    const/4 v2, 0x0

    .line 24
    new-instance v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    const-string/jumbo v1, "Main"

    invoke-direct {v0, v1, v2}, Lstarcom/snd/dialog/SettingsDialog$SettingsType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->Main:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    new-instance v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    const-string/jumbo v1, "CustomChannel"

    invoke-direct {v0, v1, v3}, Lstarcom/snd/dialog/SettingsDialog$SettingsType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->CustomChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    new-instance v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    const-string/jumbo v1, "DefaultChannel"

    invoke-direct {v0, v1, v4}, Lstarcom/snd/dialog/SettingsDialog$SettingsType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->DefaultChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    new-instance v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    const-string/jumbo v1, "EditChannel"

    invoke-direct {v0, v1, v5}, Lstarcom/snd/dialog/SettingsDialog$SettingsType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->EditChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    const/4 v0, 0x4

    new-array v0, v0, [Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    sget-object v1, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->Main:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    aput-object v1, v0, v2

    sget-object v1, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->CustomChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    aput-object v1, v0, v3

    sget-object v1, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->DefaultChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    aput-object v1, v0, v4

    sget-object v1, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->EditChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    aput-object v1, v0, v5

    sput-object v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->$VALUES:[Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 24
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lstarcom/snd/dialog/SettingsDialog$SettingsType;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .prologue
    .line 24
    const-class v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    return-object v0
.end method

.method public static values()[Lstarcom/snd/dialog/SettingsDialog$SettingsType;
    .locals 1

    .prologue
    .line 24
    sget-object v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->$VALUES:[Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    invoke-virtual {v0}, [Lstarcom/snd/dialog/SettingsDialog$SettingsType;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    return-object v0
.end method
