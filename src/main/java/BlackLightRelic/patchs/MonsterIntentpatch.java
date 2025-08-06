/*
package BlackLightRelic.patchs;

import BlackLightRelic.powers.shiyingxing;
import BlackLightRelic.powers.zuzhizaisheng;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import BlackLightRelic.powers.zuzhizaisheng;
import java.util.Iterator;

@SpirePatch(clz = AbstractPlayer.class, method = "damage")
public class MonsterIntentpatch {

    @SpireInsertPatch(rloc=)
    public static void Insert(AbstractPlayer __instance, DamageInfo  info) {
   if(AbstractDungeon.player.hasPower(shiyingxing.POWER_ID)){
       AbstractPower p=AbstractDungeon.player.getPower(shiyingxing.POWER_ID);
        tmp[0]= (float) (tmp[0]*(1- (float) p.amount /(p.amount+ Math.ceil(((float) p.owner.currentHealth /3)))));
   }

}
/*
    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
            return LineFinder.findInOrder(ctBehavior, methodCallMatcher);
        }
    }*/

