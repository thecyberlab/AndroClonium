.class Lstarcom/snd/WebRadio$1;
.super Ljava/lang/Object;
.source "WebRadio.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemSelectedListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lstarcom/snd/WebRadio;->createSpinnerListener()Landroid/widget/AdapterView$OnItemSelectedListener;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lstarcom/snd/WebRadio;


# direct methods
.method constructor <init>(Lstarcom/snd/WebRadio;)V
    .locals 0
    .param p1, "this$0"    # Lstarcom/snd/WebRadio;

    .prologue
    .line 81
    iput-object p1, p0, Lstarcom/snd/WebRadio$1;->this$0:Lstarcom/snd/WebRadio;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onItemSelected(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 1
    .param p2, "view"    # Landroid/view/View;
    .param p3, "pos"    # I
    .param p4, "id"    # J
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView",
            "<*>;",
            "Landroid/view/View;",
            "IJ)V"
        }
    .end annotation

    .prologue
    .line 85
    .local p1, "parent":Landroid/widget/AdapterView;, "Landroid/widget/AdapterView<*>;"
    iget-object v0, p0, Lstarcom/snd/WebRadio$1;->this$0:Lstarcom/snd/WebRadio;

    iget-object v0, v0, Lstarcom/snd/WebRadio;->choice:Landroid/widget/Spinner;

    invoke-virtual {v0}, Landroid/widget/Spinner;->getSelectedItem()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebRadioChannel;

    sput-object v0, Lstarcom/snd/WebRadio;->lastSelectedChannel:Lstarcom/snd/WebRadioChannel;

    .line 86
    return-void
.end method

.method public onNothingSelected(Landroid/widget/AdapterView;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView",
            "<*>;)V"
        }
    .end annotation

    .prologue
    .line 91
    .local p1, "parent":Landroid/widget/AdapterView;, "Landroid/widget/AdapterView<*>;"
    return-void
.end method
