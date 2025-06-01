package BlackLightRelic.powers;

import BlackLightRelic.helpers.ModHelper;
import BlackLightRelic.utils.TextureUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import BlackLightRelic.relics.shiyingxingzuzhi;
public class zuzhizaisheng extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("zuzhizaisheng");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int amountcounter = 0;

    public zuzhizaisheng(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = AbstractPower.PowerType.BUFF;

        this.amount = Amount;

        String path128 = "MubanResources/images/powers/zuzhizaisheng.png";
        String path48 = "MubanResources/images/powers/zuzhizaisheng.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = TextureUtils.resizeTexture(this.region128, 48, 48);

        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + (int)Math.ceil((this.amount * (this.owner.maxHealth - this.owner.currentHealth)) / 100.0D) + DESCRIPTIONS[2];

               if(AbstractDungeon.player.hasRelic(shiyingxingzuzhi.ID)){
            if(AbstractDungeon.player.getRelic(shiyingxingzuzhi.ID).usedUp){
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + (int)Math.ceil((this.amount * (this.owner.maxHealth - this.owner.currentHealth)) / 100.0D) + DESCRIPTIONS[2];

            }
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        flashWithoutSound();
        addToTop(new AbstractGameAction() {
            private AbstractCreature target = zuzhizaisheng.this.owner;
            private float duration = Settings.ACTION_DUR_FAST;
            private int amount = zuzhizaisheng.this.amount;

            public void update() {
                if ((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT) {
                    this.isDone = true;
                } else {
                    if (this.duration == Settings.ACTION_DUR_FAST) {
                        AbstractPower p = this.target.getPower(zuzhizaisheng.POWER_ID);
                        int healmaount = (int)Math.ceil((this.amount * (p.owner.maxHealth - p.owner.currentHealth)) / 100.0D);
                        if (this.target.currentHealth > 0) {
                            this.target.tint.color = Color.CHARTREUSE.cpy();
                            this.target.tint.changeColor(Color.WHITE.cpy());

                            this.target.heal(healmaount, true);
                        }

                        if (this.target.isPlayer) {
                            p = this.target.getPower(zuzhizaisheng.POWER_ID);
                            if (p != null) {
                                if (p.amount <= 0) {
                                    addToBot((AbstractGameAction)new RemoveSpecificPowerAction(p.owner, p.owner, p.ID));
                                } else {
                                    addToBot((AbstractGameAction)new ReducePowerAction(p.owner, p.owner, p.ID, (int)Math.ceil((healmaount / 10.0F))));
                                }
                            }
                        }
                    }

                    tickDuration();
                }
            }
        });
    }
}
