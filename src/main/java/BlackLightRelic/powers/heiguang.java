package BlackLightRelic.powers;

import BlackLightRelic.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class heiguang extends AbstractPower   {
    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("heiguang");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int amountcounter=0;

    public heiguang(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "MubanResources/images/powers/heiguang.png";
        String path48 = "MubanResources/images/powers/heiguang.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }
public void onSpecificTrigger(){
        this.addToBot(new AbstractGameAction() {
            heiguang power=heiguang.this;
            @Override
            public void update() {
                System.out.println("检测到黑光施加"+power.amount);
                if(power.amount>=33)
                    power.amount=0;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(power.owner,power.owner,new VulnerablePower(power.owner,6,false),6));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(power.owner,power.owner,new WeakPower(power.owner,6,false),6));
                if(power.owner instanceof AbstractMonster) {
                    AbstractDungeon.actionManager.addToBottom(new StunMonsterAction((AbstractMonster) power.owner, power.owner));
                }
                else{
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, power.owner, new StunedPower(AbstractDungeon.player)));
                }
                isDone=true;
            }
        });
}
    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }



}