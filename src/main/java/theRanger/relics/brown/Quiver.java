package theRanger.relics.brown;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRanger.DefaultMod;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.SpiritPower;
import theRanger.util.TextureLoader;

import static theRanger.DefaultMod.makeRelicOutlinePath;
import static theRanger.DefaultMod.makeRelicPath;

public class Quiver extends CustomRelic {

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Quiver");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));//TextureLoader.getTexture(makeRelicPath("Quiver_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));//TextureLoader.getTexture(makeRelicOutlinePath("Quiver_relic.png"));

    //VALUES
    private static final int SPIRIT = 2;
    private static final int ESSENCE = 2;

    public Quiver() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.SOLID);
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EssencePower(AbstractDungeon.player, ESSENCE), ESSENCE));
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SpiritPower(AbstractDungeon.player, SPIRIT), SPIRIT));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + SPIRIT + DESCRIPTIONS[1] + ESSENCE + DESCRIPTIONS[2];
    }

}
