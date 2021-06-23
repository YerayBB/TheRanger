package theRanger.potions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomPotion;
import theRanger.DefaultMod;


public class LiquidCourage extends CustomPotion {

    public static final String POTION_ID = theRanger.DefaultMod.makeID("LiquidCourage");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final int MAGIC = 20;

    public LiquidCourage() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.EYE, PotionColor.SMOKE);

        this.labOutlineColor = DefaultMod.DEFAULT_BROWN;
        // Potency is the damage/magic number equivalent of potions.
        this.isThrown = false;

        /*potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        // Do you throw this potion at an enemy or do you just consume it.


        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordProper("theranger:Vigor"),BaseMod.getKeywordDescription("theranger:Vigor")));*/

    }

    public void initializeData() {
        this.potency = getPotency();
        // Initialize the Description
        this.description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        // Initialize the on-hover name + description
        this.tips.clear();
        this.tips.add(new PowerTip(name, description));
        this.tips.add(new PowerTip(BaseMod.getKeywordTitle("theranger:Vigor"),BaseMod.getKeywordDescription("theranger:Vigor")));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractCreature p = AbstractDungeon.player;
            this.addToBot(new ApplyPowerAction(p, p, new VigorPower(p, this.potency), this.potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new LiquidCourage();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return MAGIC;
    }
}
