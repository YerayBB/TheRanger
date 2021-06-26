package theRanger.relics.brown;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theRanger.DefaultMod;
import theRanger.util.TextureLoader;

import static theRanger.DefaultMod.makeRelicOutlinePath;
import static theRanger.DefaultMod.makeRelicPath;

public class CourageMedal extends CustomRelic implements OnReceivePowerRelic {

    //TODO PATCH
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("CourageMedal");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));//TextureLoader.getTexture(makeRelicPath("CourageMedal_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));//TextureLoader.getTexture(makeRelicOutlinePath("CourageMedal_relic.png"));

    public static final int EFFECT = 1;

    public CourageMedal() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + EFFECT + this.DESCRIPTIONS[1];
    }


    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature) {
        if(abstractPower instanceof VigorPower){

            this.flash();
            abstractPower.amount++;

        }

        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature source, int stackAmount) {
        if(power instanceof VigorPower){

                this.flash();
                return stackAmount+EFFECT;

        }
        return stackAmount;
    }
}
