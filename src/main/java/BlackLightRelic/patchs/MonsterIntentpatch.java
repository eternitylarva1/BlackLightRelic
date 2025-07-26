package BlackLightRelic.patchs;

import BlackLightRelic.powers.shiyingxing;
import BlackLightRelic.powers.zuzhizaisheng;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import BlackLightRelic.powers.zuzhizaisheng;
import java.util.Iterator;

@SpirePatch(clz = AbstractMonster.class, method = "calculateDamage")
public class MonsterIntentpatch {

    @SpireInsertPatch(
           locator = Locator.class,
            localvars = {"tmp"}
    )
    public static void Insert(AbstractMonster __instance,int dmg,@ByRef float[] tmp ) {
   if(AbstractDungeon.player.hasPower(shiyingxing.POWER_ID)){
       AbstractPower p=AbstractDungeon.player.getPower(shiyingxing.POWER_ID);
        tmp[0]= (float) (tmp[0]*(1- (float) p.amount /(p.amount+ Math.ceil(((float) p.owner.currentHealth /3)))));
   }

}

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
            return LineFinder.findInOrder(ctBehavior, methodCallMatcher);
        }
    }

}