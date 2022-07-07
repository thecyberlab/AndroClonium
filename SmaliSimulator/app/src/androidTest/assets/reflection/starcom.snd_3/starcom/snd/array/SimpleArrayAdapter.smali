.class public Lstarcom/snd/array/SimpleArrayAdapter;
.super Landroid/widget/BaseAdapter;
.source "SimpleArrayAdapter.java"


# instance fields
.field private channels:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;"
        }
    .end annotation
.end field

.field private final context:Landroid/content/Context;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .prologue
    .line 21
    invoke-direct {p0}, Landroid/widget/BaseAdapter;-><init>()V

    .line 18
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lstarcom/snd/array/SimpleArrayAdapter;->channels:Ljava/util/ArrayList;

    .line 22
    iput-object p1, p0, Lstarcom/snd/array/SimpleArrayAdapter;->context:Landroid/content/Context;

    .line 23
    return-void
.end method


# virtual methods
.method public add(Lstarcom/snd/WebRadioChannel;)V
    .locals 1
    .param p1, "curChannel"    # Lstarcom/snd/WebRadioChannel;

    .prologue
    .line 62
    iget-object v0, p0, Lstarcom/snd/array/SimpleArrayAdapter;->channels:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    invoke-virtual {p0}, Lstarcom/snd/array/SimpleArrayAdapter;->notifyDataSetChanged()V

    return-void
.end method

.method public addAll(Ljava/util/List;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 63
    .local p1, "curChannels":Ljava/util/List;, "Ljava/util/List<Lstarcom/snd/WebRadioChannel;>;"
    iget-object v0, p0, Lstarcom/snd/array/SimpleArrayAdapter;->channels:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    invoke-virtual {p0}, Lstarcom/snd/array/SimpleArrayAdapter;->notifyDataSetChanged()V

    return-void
.end method

.method public clear()V
    .locals 1

    .prologue
    .line 64
    iget-object v0, p0, Lstarcom/snd/array/SimpleArrayAdapter;->channels:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    invoke-virtual {p0}, Lstarcom/snd/array/SimpleArrayAdapter;->notifyDataSetChanged()V

    return-void
.end method

.method public getCount()I
    .locals 1

    .prologue
    .line 47
    iget-object v0, p0, Lstarcom/snd/array/SimpleArrayAdapter;->channels:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    return v0
.end method

.method public getItem(I)Ljava/lang/Object;
    .locals 1
    .param p1, "position"    # I

    .prologue
    .line 53
    iget-object v0, p0, Lstarcom/snd/array/SimpleArrayAdapter;->channels:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    return-object v0
.end method

.method public getItemId(I)J
    .locals 2
    .param p1, "position"    # I

    .prologue
    .line 59
    int-to-long v0, p1

    return-wide v0
.end method

.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 11
    .param p1, "pos"    # I
    .param p2, "convertView"    # Landroid/view/View;
    .param p3, "parent"    # Landroid/view/ViewGroup;

    .prologue
    .line 28
    iget-object v5, p0, Lstarcom/snd/array/SimpleArrayAdapter;->context:Landroid/content/Context;

    const-string/jumbo v6, "layout_inflater"

    .line 29
    invoke-virtual {v5, v6}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/view/LayoutInflater;

    .line 30
    .local v3, "inflater":Landroid/view/LayoutInflater;
    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    const/4 v10, 0x0

    aput-object v8, v7, v10

    const/16 v9, 0x31

    invoke-static {v9, p0, v7}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Object;

    check-cast v1, Lstarcom/snd/WebRadioChannel;

    .line 32
    .local v1, "curChannel":Lstarcom/snd/WebRadioChannel;
    move-object v4, p2

    .line 33
    .local v4, "rowView":Landroid/view/View;
    if-nez v4, :cond_0

    .line 35
    const v5, 0x7f030003

    const/4 v6, 0x0

    invoke-virtual {v3, v5, p3, v6}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object v4

    .line 37
    :cond_0
    const v5, 0x7f020005

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 38
    .local v0, "channelTxt":Landroid/widget/TextView;
    const v5, 0x7f02000d

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/ImageView;

    .line 39
    .local v2, "imageView":Landroid/widget/ImageView;
    const/4 v7, 0x0

    const/16 v9, 0x32

    invoke-static {v9, v1, v7}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/Integer;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v5

    invoke-virtual {v2, v5}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 40
    const/4 v7, 0x0

    const/16 v9, 0x33

    invoke-static {v9, v1, v7}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-virtual {v0, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 41
    return-object v4
.end method
