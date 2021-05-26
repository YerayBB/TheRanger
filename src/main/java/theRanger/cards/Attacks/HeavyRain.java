package theRanger.cards.Attacks;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.StandardShotAction;
import theRanger.cards.AbstractInfusedCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.InfusedPower;

import java.util.ArrayList;
import java.util.Iterator;

import static theRanger.DefaultMod.makeCardPath;

public class HeavyRain extends AbstractInfusedCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(HeavyRain.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("HeavyRain.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 5;

    private static final int INFUSE = 5;
    private static final int UPGRADE_PLUS_INFUSE = 3;

    private static final int SHOTAMOUNT = 3;

    // /STAT DECLARATION/


    public HeavyRain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseInfuseNumber = INFUSE;
    }

//TODO
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList tmp = new ArrayList();
        Iterator var = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while (var.hasNext()){
            AbstractMonster mo = (AbstractMonster) var.next();
            if(!mo.isDying && !mo.isEscaping && !mo.halfDead){
                tmp.add(mo);
            }
        }
        boolean first = true;
        while(!tmp.isEmpty()){
            AbstractMonster mo = (AbstractMonster) tmp.get(MathUtils.random(0, tmp.size() - 1));
            if(first){
                addToBot(new StandardShotAction(SHOTAMOUNT,new AbstractGameAction[]{
                        new DamageAction(mo, new DamageInfo(p,this.damage,this.damageTypeForTurn)),
                        new ApplyPowerAction(mo,p,new InfusedPower(mo,p,this.infuseNumber),this.infuseNumber),
                        new VFXAction(new BlizzardEffect(20, true))}));
                first = false;
            }else{
                addToBot(new StandardShotAction(SHOTAMOUNT,new AbstractGameAction[]{
                        new DamageAction(mo, new DamageInfo(p,this.damage,this.damageTypeForTurn)),
                        new ApplyPowerAction(mo,p,new InfusedPower(mo,p,this.infuseNumber),this.infuseNumber)}));
            }
            tmp.remove(mo);
        }
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        int aux = 0;
        Iterator var = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while (var.hasNext()){
            AbstractMonster mo = (AbstractMonster) var.next();
            if(!mo.isDying && !mo.isEscaping && !mo.halfDead){
                aux += SHOTAMOUNT;
            }
        }
        this.rawDescription += aux + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hasPower(EssencePower.POWER_ID)) {
            int amount = AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount;
            int count = 0;
            Iterator var = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while(var.hasNext()) {
                AbstractMonster mo = (AbstractMonster) var.next();
                if (!mo.isDeadOrEscaped()) {
                    count++;
                }
            }
            if(count*SHOTAMOUNT <= amount) this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }

    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeInfuseNumber(UPGRADE_PLUS_INFUSE);
            initializeDescription();
        }
    }
}
