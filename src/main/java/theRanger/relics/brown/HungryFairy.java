package theRanger.relics.brown;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRanger.DefaultMod;
import theRanger.powers.brown.EssencePower;
import theRanger.util.TextureLoader;

import static theRanger.DefaultMod.makeRelicOutlinePath;
import static theRanger.DefaultMod.makeRelicPath;

public class HungryFairy extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("HungryFairy");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));//TextureLoader.getTexture(makeRelicPath("HungryFairy_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));//TextureLoader.getTexture(makeRelicOutlinePath("HungryFairy_relic.png"));

    private static final int REQUIRED = 1;
    private static final int ENERGYGAIN = 1;

    public HungryFairy() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStartPostDraw() {
        this.flash();
        if(AbstractDungeon.player.hasPower(EssencePower.POWER_ID)){
            if(AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount >= REQUIRED){
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, EssencePower.POWER_ID, REQUIRED));
                addToBot(new GainEnergyAction(ENERGYGAIN));
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
