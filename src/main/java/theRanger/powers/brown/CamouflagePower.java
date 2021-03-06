package theRanger.powers.brown;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theRanger.DefaultMod;
import theRanger.util.TextureLoader;

import static theRanger.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class CamouflagePower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("CamouflagePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public CamouflagePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.priority = 4;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {

        this.description = DESCRIPTIONS[0];

    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.ATTACK){
            if(!this.owner.hasPower("theRanger:ShadowFormPower")){
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0) {
            if (damageAmount < this.amount) {
                this.flash();
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                return 0;
            }
            else{
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                return damageAmount;
            }
        }
        return damageAmount;
    }
}