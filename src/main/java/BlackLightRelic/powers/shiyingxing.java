package BlackLightRelic.powers;

import BlackLightRelic.helpers.ModHelper;
import BlackLightRelic.utils.TextureUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.TinyHouse;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class shiyingxing extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("shiyingxing");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int amountcounter=0;
    private boolean justApplied = false;
     private int turndamageamount = 0;
    public shiyingxing(AbstractCreature owner, int Amount,boolean isSourceMonster) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "MubanResources/images/powers/shiyingxing.png";
        String path48 = "MubanResources/images/powers/shiyingxing.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = TextureUtils.resizeTexture(this.region128, 48, 48);

        this.justApplied = true;

        this.isTurnBased = true;
        // 首次添加能力更新描述
        this.updateDescription();}
    public shiyingxing(AbstractCreature owner, int Amount) {
   this(owner,Amount,false);
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount*100 /(this.amount+ Math.ceil(((float) this.owner.currentHealth /3)))+ DESCRIPTIONS[1];
    }
    public void atEndOfRound() {

        if (this.justApplied) {
            this.justApplied = false;
        } else {
            if (this.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, (int) Math.ceil((double)turndamageamount / 20F)));
            }

        }
        turndamageamount=0;
    }




    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            turndamageamount+= (int) (damageAmount*((float) this.amount /(this.amount+ Math.ceil(((float) this.owner.currentHealth /3)))));
            return (int) (damageAmount*(1- (float) this.amount /(this.amount+ Math.ceil(((float) this.owner.currentHealth /3)))));
        } else {
            return damageAmount;
        }
    }
    // 能力在更新时如何修改描述




}