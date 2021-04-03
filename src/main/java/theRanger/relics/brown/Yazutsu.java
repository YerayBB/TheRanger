package theRanger.relics.brown;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theRanger.DefaultMod;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.SpiritPower;
import theRanger.util.TextureLoader;

import static theRanger.DefaultMod.*;

public class Yazutsu extends CustomRelic {

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Yazutsu");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));//TextureLoader.getTexture(makeRelicPath("Yazutsu_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));//TextureLoader.getTexture(makeRelicOutlinePath("Yazutsu_relic.png"));

    //VALUES
    private static final int SPIRIT = 7;
    private static final int ESSENCE = 5;


    public Yazutsu() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        logger.info("Starting to add");

        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EssencePower(AbstractDungeon.player, ESSENCE), ESSENCE));
        logger.info("Essence In");
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SpiritPower(AbstractDungeon.player, SPIRIT), SPIRIT));
        logger.info("Spirit In");
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        logger.info("Done with above");
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + SPIRIT + DESCRIPTIONS[1] + ESSENCE + DESCRIPTIONS[2];
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(Quiver.ID);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(Quiver.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if ((AbstractDungeon.player.relics.get(i)).relicId.equals(Quiver.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }
}
