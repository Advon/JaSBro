package jasbro.game.character.traits;

import jasbro.Util;
import jasbro.game.character.Charakter;
import jasbro.game.character.activities.ActivityType;
import jasbro.game.character.activities.BusinessMainActivity;
import jasbro.game.character.activities.BusinessSecondaryActivity;
import jasbro.game.character.activities.RunningActivity;
import jasbro.game.character.activities.sub.Orgy;
import jasbro.game.character.activities.sub.Sex;
import jasbro.game.character.activities.sub.business.Strip;
import jasbro.game.character.activities.sub.business.SubmitToMonster;
import jasbro.game.character.activities.sub.whore.Whore;
import jasbro.game.character.attributes.Attribute;
import jasbro.game.character.attributes.AttributeModification;
import jasbro.game.character.attributes.BaseAttributeTypes;
import jasbro.game.character.attributes.CalculatedAttribute;
import jasbro.game.character.attributes.EssentialAttributes;
import jasbro.game.character.attributes.Sextype;
import jasbro.game.character.battle.Attack;
import jasbro.game.character.specialization.SpecializationAttribute;
import jasbro.game.events.EventType;
import jasbro.game.events.MessageData;
import jasbro.game.events.MyEvent;
import jasbro.game.events.business.Customer;
import jasbro.game.events.business.CustomerGroup;
import jasbro.game.events.business.CustomerType;
import jasbro.game.interfaces.AttributeType;
import jasbro.gui.pictures.ImageTag;
import jasbro.gui.pictures.ImageUtil;
import jasbro.texts.TextUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class TraitEffect {

    private final static float ATTRIBUTEMODIFICATOR = 0.3f;

    public void handleEvent(MyEvent e, Charakter character, Trait trait) {
    }
    
    public boolean removeTrait(Charakter character) {
        return true;
    }
    
    public boolean addTrait(Charakter character) {
        return true;
    }
    
    public int getMinObedienceModified(int curMinObedience, Charakter character, RunningActivity activity) {
        return curMinObedience;
    }
    
    public SkillTree getSkillTree() {
        return null;
    }

    public double getAttributeModified(CalculatedAttribute calculatedAttribute, double currentValue, Charakter character) {
        return currentValue;
    }
    
    public int modifyCustomerRating(int initialRating, Customer customer) {
        return initialRating;
    }

    public void modifyPossibleAttacks(List<Attack> attacks, Charakter character) {
    }
    
    public float getAttributeModifier(Attribute attribute) {
        return 0;
    }

    public final static class AttributeChangeInfluence extends TraitEffect {
        private AttributeType attributeType;
        private float attributeModifier;

        public AttributeChangeInfluence(AttributeType attributeType, float attributeModifier) {
            super();
            this.attributeType = attributeType;
            this.attributeModifier = attributeModifier;
        }

        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == attributeType) {
                    float modification = attributeModification.getBaseAmount();
                    float change = Math.abs(modification) * attributeModifier;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    
    public static class AllAttributeChangeInfluence extends TraitEffect {
        private float attributeModifier;

        public AllAttributeChangeInfluence(float attributeModifier) {
            super();
            this.attributeModifier = attributeModifier;
        }

        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (!(attributeModification.getAttributeType() instanceof EssentialAttributes) &&
                        attributeModification.getBaseAmount() > 0) {
                    float modification = attributeModification.getBaseAmount();
                    float change = Math.abs(modification) * attributeModifier;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    
    public static class BaseAttributeChangeInfluence extends TraitEffect {
        private float attributeModifier;

        public BaseAttributeChangeInfluence(float attributeModifier) {
            super();
            this.attributeModifier = attributeModifier;
        }

        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() instanceof BaseAttributeTypes &&
                        attributeModification.getBaseAmount() > 0) {
                    float modification = attributeModification.getBaseAmount();
                    float change = Math.abs(modification) * attributeModifier;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    
    public static class SexAttributeChangeInfluence extends TraitEffect {
        private float attributeModifier;

        public SexAttributeChangeInfluence(float attributeModifier) {
            super();
            this.attributeModifier = attributeModifier;
        }

        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() instanceof Sextype &&
                        attributeModification.getBaseAmount() > 0) {
                    float modification = attributeModification.getBaseAmount();
                    float change = Math.abs(modification) * attributeModifier;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    
    public static class SpecializationAttributeChangeInfluence extends TraitEffect {
        private float attributeModifier;

        public SpecializationAttributeChangeInfluence(float attributeModifier) {
            super();
            this.attributeModifier = attributeModifier;
        }

        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() instanceof SpecializationAttribute &&
                        attributeModification.getBaseAmount() > 0) {
                    float modification = attributeModification.getBaseAmount();
                    float change = Math.abs(modification) * attributeModifier;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    
    public final static class MultipleAttributeChangeInfluence extends TraitEffect {
        private AttributeType attributeTypes [];
        private float attributeModifier;

        public MultipleAttributeChangeInfluence(float attributeModifier, AttributeType... attributeTypes) {
            super();
            this.attributeTypes = attributeTypes;
            this.attributeModifier = attributeModifier;
        }

        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                for (AttributeType attributeType : attributeTypes) {
                    if (attributeModification.getAttributeType() == attributeType) {
                        float modification = attributeModification.getBaseAmount();
                        float change = Math.abs(modification) * attributeModifier;
                        attributeModification.addModificator(change);
                        break;
                    }
                }
            }
        }
    }
    
    public final static class AttributeMaxModifier extends TraitEffect {
        private AttributeType attributeType;
        private int attributeMaxModifier;

        public AttributeMaxModifier(AttributeType attributeType, int attributeMaxModifier) {
            super();
            this.attributeType = attributeType;
            this.attributeMaxModifier = attributeMaxModifier;
        }

        @Override
        public boolean addTrait(Charakter character) {
            Attribute attribute = character.getAttribute(attributeType);
            attribute.setMaxValue(attribute.getMaxValue() + attributeMaxModifier);
            return true;
        }
        
        @Override
        public boolean removeTrait(Charakter character) {
            Attribute attribute = character.getAttribute(attributeType);
            attribute.setMaxValue(attribute.getMaxValue() - attributeMaxModifier);
            return true;
        }
    }
    
    
    public final static class BigBoobs extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ACTIVITY) {
                RunningActivity activity = (RunningActivity) e.getSource();
                if (activity instanceof BusinessMainActivity) {
                    BusinessMainActivity businessMainActivity = (BusinessMainActivity) activity;
                    for (Customer customer : businessMainActivity.getMainCustomers()) {
                        customer.addToSatisfaction(5, trait);
                    }
                }
                if (activity instanceof BusinessSecondaryActivity) {
                    BusinessSecondaryActivity businessMainActivity = (BusinessSecondaryActivity) activity;
                    for (Customer customer : businessMainActivity.getCustomers()) {
                        customer.addToSatisfaction(1, trait);
                    }
                }
            }
        }
    }

    public final static class SmallBoobs extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ACTIVITY) {
                RunningActivity activity = (RunningActivity) e.getSource();
                if (activity instanceof BusinessMainActivity) {
                    BusinessMainActivity businessMainActivity = (BusinessMainActivity) activity;
                    if (!(activity instanceof SubmitToMonster)) {
                        if (businessMainActivity.getMainCustomers().size() == 1) {
                            int rnd = Util.getInt(0, 100);
                            MessageData message = activity.getMessages().get(0);
                            if (rnd < 20) {
                                message.addToMessage("\n"+TextUtil.t("SMALLBOOBS.like"));
                                businessMainActivity.getMainCustomers().get(0).addToSatisfaction(15, trait);
                            } else if (rnd > 70) {
                                message.addToMessage("\n"+TextUtil.t("SMALLBOOBS.dislike"));
                                businessMainActivity.getMainCustomers().get(0).addToSatisfaction(-10, trait);
                            }
                        }
                    }
                }
            }
        }
    }

    // Loli trait. may greatly increase or decrease cust satisfaction
    public final static class Loli extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ACTIVITY) {
                RunningActivity activity = (RunningActivity) e.getSource();
                if (activity instanceof Whore) {
                    BusinessMainActivity businessMainActivity = (BusinessMainActivity) activity;
                    if (businessMainActivity.getMainCustomers().size() == 1) {
                        int rnd = Util.getInt(0, 100); // 50% dislike, 30% like, 20% don't mind
                        MessageData message = activity.getMessages().get(0);
                        if (rnd < 30) {
                            message.addToMessage("\n"+TextUtil.t("LOLI.like", character));
                            businessMainActivity.getMainCustomers().get(0).addToSatisfaction(60, trait);
                        } else if (rnd > 50) {
                            message.addToMessage("\n"+TextUtil.t("LOLI.dislike", character));
                            businessMainActivity.getMainCustomers().get(0).addToSatisfaction(-60, trait);
                        }
                    }

                }
            }
        }
    }
    
    // Furry trait. may greatly increase or decrease cust satisfaction
    public final static class Furry extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ACTIVITY) {
                RunningActivity activity = (RunningActivity) e.getSource();
                if (activity instanceof Whore) {
                    BusinessMainActivity businessMainActivity = (BusinessMainActivity) activity;
                    if (businessMainActivity.getMainCustomers().size() == 1) {
                        int rnd = Util.getInt(0, 100); // 40% dislike, 40% like, 20% don't mind
                        MessageData message = activity.getMessages().get(0);
                        if (rnd < 40) {
                            message.addToMessage("\n"+TextUtil.t("FURRY.like", character));
                            businessMainActivity.getMainCustomers().get(0).addToSatisfaction(60, trait);
                        } else if (rnd > 60) {
                            message.addToMessage("\n"+TextUtil.t("FURRY.dislike", character));
                            businessMainActivity.getMainCustomers().get(0).addToSatisfaction(-60, trait);
                        }
                    }

                }
            }
        }
    }    
    
    public final static class Absorption extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ACTIVITY) {
                RunningActivity activity = (RunningActivity) e.getSource();
                if (activity.getType() == ActivityType.ORGY
                        || (activity instanceof Whore && activity.getMainCustomer() != null && 
                            activity.getMainCustomer().getType() == CustomerType.GROUP)) {
                    MessageData message = activity.getMessages().get(0);
                    message.addToMessage("\n"+TextUtil.t("ABSORPTION.perform", character));
                    int amountPeople;
                    if (activity.getType() == ActivityType.ORGY) {
                        amountPeople = activity.getCharacters().size();
                    }
                    else {
                        amountPeople = ((CustomerGroup)activity.getMainCustomer()).getCustomers().size();
                    }
                    activity.getAttributeModifications().add(new AttributeModification(2.5f * amountPeople, EssentialAttributes.ENERGY, character));
                }
            }
        }
    }
    
    // shy trait, gets bright red at the slightest touch, some people like
    // that.
    public final static class Shy extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == SpecializationAttribute.SEDUCTION) {
                    float modification = attributeModification.getBaseAmount();
                    float change = -Math.abs(modification) * ATTRIBUTEMODIFICATOR;
                    attributeModification.addModificator(change);
                }
            }
            if (e.getType() == EventType.ACTIVITY) {
                RunningActivity activity = (RunningActivity) e.getSource();
                if (activity instanceof Whore) {
                    BusinessMainActivity businessMainActivity = (BusinessMainActivity) activity;
                    if (!(activity instanceof SubmitToMonster)) {
                        if (businessMainActivity.getMainCustomers().size() == 1) {
                            int rnd = Util.getInt(0, 100); // 15% dislike, 30% like, 55% don't mind
                            MessageData message = activity.getMessages().get(0);
                            if (rnd < 30) {
                                message.addToMessage("\n"+TextUtil.t("SHY.like", character)); // this may overcrowd the text message...
                                businessMainActivity.getMainCustomers().get(0).addToSatisfaction(10, trait);
                            } else if (rnd > 85) {
                                message.addToMessage("\n"+TextUtil.t("SHY.dislike", character));
                                businessMainActivity.getMainCustomers().get(0).addToSatisfaction(-10, trait);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public final static class Uninhibited extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == SpecializationAttribute.SEDUCTION) {
                    float modification = attributeModification.getBaseAmount();
                    float change = Math.abs(modification) * 0.3f;
                    attributeModification.addModificator(change);
                }
            }
            if (e.getType() == EventType.ACTIVITY) {
            RunningActivity activity = (RunningActivity) e.getSource();
                if (activity instanceof BusinessMainActivity) {
                    if (activity instanceof BusinessMainActivity) {
                        BusinessMainActivity businessMainActivity = (BusinessMainActivity) activity;
                        if (!(activity instanceof SubmitToMonster)) {
                            if (businessMainActivity.getMainCustomers().size() == 1) {
                                int rnd = Util.getInt(0, 100); // 15% dislike, 15% like, 70% don't mind
                                MessageData message = activity.getMessages().get(0);
                                if (rnd < 15) {
                                	message.addToMessage("\n"+TextUtil.t("UNINHIBITED.dislike", character));
                                	businessMainActivity.getMainCustomers().get(0).addToSatisfaction(10, trait);
                                } else if (rnd > 85) {
                                	message.addToMessage("\n"+TextUtil.t("UNINHIBITED.like", character));
                                    businessMainActivity.getMainCustomers().get(0).addToSatisfaction(-10, trait);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public final static class OpenMinded extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.GROUP
                		|| attributeModification.getAttributeType() == Sextype.MONSTER
                		|| attributeModification.getAttributeType() == Sextype.BONDAGE) {
                    float modification = attributeModification.getBaseAmount();
                    float change = Math.abs(modification) * 0.3f;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    public final static class Reserved extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.GROUP
                		|| attributeModification.getAttributeType() == Sextype.MONSTER
                		|| attributeModification.getAttributeType() == Sextype.BONDAGE) {
                    float modification = attributeModification.getBaseAmount();
                    float change = -Math.abs(modification) * 0.3f;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    public final static class AmbitousLover extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.VAGINAL
                		|| attributeModification.getAttributeType() == Sextype.ANAL) {
                    float modification = attributeModification.getBaseAmount();
                    float change = Math.abs(modification) * 0.15f;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    public final static class DeadFish extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.VAGINAL
                		|| attributeModification.getAttributeType() == Sextype.ANAL) {
                    float modification = attributeModification.getBaseAmount();
                    float change = -Math.abs(modification) * 0.15f;
                    attributeModification.addModificator(change);
                }
            }
        }
    }   
    public final static class Sensitive extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.FOREPLAY) {
                    float modification = attributeModification.getBaseAmount();
                    float change = -Math.abs(modification) * 0.3f;
                    attributeModification.addModificator(change);
                }
            }
        }
    }    
    public final static class Numb extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.FOREPLAY) {
                    float modification = attributeModification.getBaseAmount();
                    float change = -Math.abs(modification) * 0.3f;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    public final static class Multifaceted extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.GROUP) {
                    float modification = attributeModification.getBaseAmount();
                    float change = -Math.abs(modification) * 0.3f;
                    attributeModification.addModificator(change);
                }
            }
        }
    }    
    public final static class SingleMinded extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.GROUP) {
                    float modification = attributeModification.getBaseAmount();
                    float change = -Math.abs(modification) * 0.3f;
                    attributeModification.addModificator(change);
                }
            }
        }
    }     
    public final static class Biter extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.ORAL) {
                    float modification = attributeModification.getBaseAmount();
                    float change = -Math.abs(modification) * 0.3f;
                    attributeModification.addModificator(change);
                }
            }
        }
    }    
    public final static class SensualTongue extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.ORAL) {
                    float modification = attributeModification.getBaseAmount();
                    float change = -Math.abs(modification) * 0.3f;
                    attributeModification.addModificator(change);
                }
            }
        }
    }     
    
    // Nice body trait, girl il good at stripping TODO (I don't know how to
    // change customer satisfaction on a secondary activity)
    public final static class Nicebody extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == SpecializationAttribute.STRIP) {
                    float modification = attributeModification.getBaseAmount();
                    float change = Math.abs(modification) * ATTRIBUTEMODIFICATOR;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    
    // slut trait, girl performs better with rich customers and treat poor
    // customers like crap
    public final static class Slut extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ACTIVITY) {
                RunningActivity activity = (RunningActivity) e.getSource();
                if (activity instanceof BusinessMainActivity) {
                    BusinessMainActivity businessMainActivity = (BusinessMainActivity) activity;
                    if (activity instanceof Whore) {
                        if ((activity.getMainCustomer().getType() == CustomerType.GROUP
                                || activity.getMainCustomer().getType() == CustomerType.CELEBRITY
                                || activity.getMainCustomer().getType() == CustomerType.LORD
                                || activity.getMainCustomer().getType() == CustomerType.MINORNOBLE || activity
                                .getMainCustomer().getType() == CustomerType.BUSINESSMAN)) {
                            MessageData message = activity.getMessages().get(0);
                            message.addToMessage("\n"+TextUtil.t("SLUT.rich", character, activity.getMainCustomer()));
                            businessMainActivity.getMainCustomers().get(0).addToSatisfaction(25, trait);
                        } else if (activity.getMainCustomer().getType() == CustomerType.BUM
                                || activity.getMainCustomer().getType() == CustomerType.PEASANT) {
                            MessageData message = activity.getMessages().get(0);
                            message.addToMessage("\n"+TextUtil.t("SLUT.poor", character, activity.getMainCustomer()));
                            businessMainActivity.getMainCustomers().get(0).addToSatisfaction(-25, trait);
                        }
                    }
                }
                else if (activity instanceof Strip) {
                    if(activity.getCustomers().size() >= 20){
                        activity.getMessages().get(0).addToMessage("\n"+TextUtil.t("SLUT.strip", character));
                        for (Customer customer : activity.getCustomers()) {
                            customer.addToSatisfaction(1 + character.getFinalValue(SpecializationAttribute.STRIP) / 20, activity);
                        }
                    }
                }
            }
        }
    }
    
    public final static class Fragile extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ACTIVITY) {
                RunningActivity activity = (RunningActivity) e.getSource();
                if (activity instanceof BusinessMainActivity) {
                    BusinessMainActivity businessMainActivity = (BusinessMainActivity) activity;
                    if ((activity instanceof SubmitToMonster)) {
                        if (businessMainActivity.getMainCustomers().size() == 1) {
                            character.getAttribute(EssentialAttributes.HEALTH).addToValue(-15, activity);
                            character.getAttribute(EssentialAttributes.ENERGY).addToValue(-15, activity);
                            activity.getMessages().get(0).addToMessage("\n"+TextUtil.t("FRAGILE.monster", character));
                        }
                    }
                    else if (activity instanceof Whore) {
                        Whore whoreActivity = (Whore) activity;
                        if ((whoreActivity.getSexType() == Sextype.BONDAGE || whoreActivity.getSexType() == Sextype.GROUP) && 
                                businessMainActivity.getMainCustomers().size() > 0) {
                            character.getAttribute(EssentialAttributes.HEALTH).addToValue(-7, activity);
                            character.getAttribute(EssentialAttributes.ENERGY).addToValue(-7, activity);
                            activity.getMessages().get(0).addToMessage("\n"+TextUtil.t("FRAGILE.kinky", character));
                        }
                    }
                }
            }
        }
    }
    
    //wild trait, growth of monster skill increased, submitting costs less health, growth of Str nd Sta increased.
    public final static class Wild extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == Sextype.MONSTER
                		|| attributeModification.getAttributeType() == BaseAttributeTypes.STAMINA
                		|| attributeModification.getAttributeType() == BaseAttributeTypes.STRENGTH) {
                    float modification = attributeModification.getBaseAmount();
                    float change = Math.abs(modification) * ATTRIBUTEMODIFICATOR;
                    attributeModification.addModificator(change);
                }
                if (attributeModification.getAttributeType() == BaseAttributeTypes.OBEDIENCE) {
                    float modification = attributeModification.getBaseAmount();
                    float change = -Math.abs(modification) * ATTRIBUTEMODIFICATOR;
                    attributeModification.addModificator(change);
                }
            } else if (e.getType() == EventType.ACTIVITY) {
                RunningActivity activity = (RunningActivity) e.getSource();
                if (activity instanceof BusinessMainActivity) {
                    BusinessMainActivity businessMainActivity = (BusinessMainActivity) activity;
                    if (activity instanceof SubmitToMonster) {
                        if (businessMainActivity.getMainCustomers().size() == 1) {
                            MessageData message = activity.getMessages().get(0);
                                message.addToMessage("\n"+TextUtil.t("WILD.like", character));
                                businessMainActivity.getMainCustomers().get(0).addToSatisfaction(10, trait);
                                for (AttributeModification attributeModification : activity.getAttributeModifications()) {
        	                        if (attributeModification.getAttributeType() == EssentialAttributes.HEALTH) {
        	                                float modification = attributeModification.getBaseAmount();
        	                                float change = Math.abs(modification)*0.2f;
        	                                attributeModification.addModificator(change);
        	                            }
        	                        if (attributeModification.getAttributeType() == EssentialAttributes.ENERGY) {
                                        float modification = attributeModification.getBaseAmount();
                                        float change = Math.abs(modification)*0.1f;
                                        attributeModification.addModificator(change);
                                    }
                                }
        	                                            
                        }
                    }
                }
            }
        }
    }
    
    
    public final static class Tsundere extends TraitEffect {
        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ACTIVITY) {
                RunningActivity activity = (RunningActivity) e.getSource();
                if (activity.isAbort()) {
                    return;
                }
                if (activity instanceof Whore && activity.getType() != ActivityType.TEASE) {
                    if (activity instanceof BusinessMainActivity) {
                        BusinessMainActivity businessMainActivity = (BusinessMainActivity) activity;
                        if (businessMainActivity.getMainCustomers().size() > 0) {
                            int rnd = Util.getInt(0, 100);
                            int chance = 25 - character.getObedience() * 2;
                            if (businessMainActivity instanceof Whore) {
                                chance += 10;
                            }
                            if (chance < 1) {
                                chance = 1;
                            }
                            if (rnd < chance) { // does the girl hit the customer?
                                MessageData message = activity.getMessages().get(0);
                                message.addToMessage(TextUtil.t("TSUNDERE.hit", character, businessMainActivity.getMainCustomers().get(0)));
                                businessMainActivity.getMainCustomers().get(0).addToSatisfaction((int) (-character.getDamage() * 10 * 2), trait);
                                activity.getAttributeModifications().add(new AttributeModification(-0.05f, BaseAttributeTypes.OBEDIENCE, character));

                                int chanceKo = (int) (character.getDamage() * 10);
                                rnd = Util.getInt(0, 100);

                                if (rnd < chanceKo) {
                                    activity.setAbort(true);
                                    List<AttributeModification> attributeModifications = new ArrayList<AttributeModification>();
                                    attributeModifications.add(new AttributeModification(-20f, EssentialAttributes.ENERGY, character));
                                    attributeModifications.add(new AttributeModification(-0.05f, BaseAttributeTypes.OBEDIENCE, character));
                                    message.addToMessage(TextUtil.t("TSUNDERE.hitKO", character, businessMainActivity.getMainCustomers().get(0)));
                                    message.setBackground(character.getBackground());
                                    message.setImage(ImageUtil.getInstance().getImageDataByTag(ImageTag.FIGHT, character));
                                    message.setAttributeModifications(attributeModifications);
                                    message.createMessageScreen();
                                } else {
                                    rnd = Util.getInt(0, 100);
                                    int chanceAbort = (-businessMainActivity.getMainCustomers().get(0).getSatisfactionAmount()) - (int) (character.getDamage() * 4) - character.getCharisma() / 2;

                                    if (rnd < chanceAbort) {
                                        activity.setAbort(true);
                                        List<AttributeModification> attributeModifications = new ArrayList<AttributeModification>();
                                        attributeModifications.add(new AttributeModification(-20f, EssentialAttributes.ENERGY, character));
                                        attributeModifications.add(new AttributeModification(-10f, EssentialAttributes.HEALTH, character));
                                        attributeModifications.add(new AttributeModification(-0.05f, BaseAttributeTypes.OBEDIENCE, character));
                                        message.addToMessage(TextUtil.t("TSUNDERE.hitBack", character, businessMainActivity.getMainCustomers().get(0)));
                                        message.setBackground(character.getBackground());
                                        message.setImage(ImageUtil.getInstance().getImageDataByTag(ImageTag.FIGHT, character));
                                        message.setAttributeModifications(attributeModifications);
                                        message.createMessageScreen();
                                    } else {
                                        message.addToMessage(TextUtil.t("TSUNDERE.continue", character, businessMainActivity.getMainCustomers().get(0)));
                                    }
                                }
                            }
                        }
                    } else if (activity instanceof BusinessSecondaryActivity) {

                    }
                }
            }
        }
    }
    
    public final static class Obedient extends TraitEffect {
        @Override
        public int getMinObedienceModified(int curMinObedience, Charakter character, RunningActivity activity) {
            return curMinObedience - 2;
        }
    }
    
    public final static class Feisty extends TraitEffect {
        @Override
        public int getMinObedienceModified(int curMinObedience, Charakter character, RunningActivity activity) {
            return curMinObedience + 2;
        }
    }
    
    public final static class Nympho extends TraitEffect {
        @Override
        public int getMinObedienceModified(int curMinObedience, Charakter character, RunningActivity activity) {
            if (activity != null) {
                if (activity instanceof Whore || activity instanceof Sex || activity instanceof Orgy) {
                    return curMinObedience - 2;
                } else {
                    return curMinObedience;
                }
            } else {
                return curMinObedience;
            }
        }
        
        @Override
        public double getAttributeModified(CalculatedAttribute calculatedAttribute, double currentValue, Charakter character) {
            if (calculatedAttribute == CalculatedAttribute.AMOUNTCUSTOMERSPERSHIFT) {
                return currentValue + 1;
            }
            else {
                return currentValue;
            }
        }
    }
    
    public static class InfluenceAttributeLoss extends TraitEffect {
        private AttributeType attributeType;
        private float attributeModifier;
        
        public InfluenceAttributeLoss(AttributeType attributeType, float attributeModifier) {
            this.attributeType = attributeType;
            this.attributeModifier = attributeModifier;
        }

        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == attributeType && attributeModification.getBaseAmount() < 0) {
                    float modification = attributeModification.getBaseAmount();
                    float change = modification * attributeModifier;
                    attributeModification.addModificator(change);
                }
            }
        }
    }
    
    public static class CancelAttributeLoss extends TraitEffect {
        private AttributeType attributeType;
        
        public CancelAttributeLoss(AttributeType attributeType) {
            this.attributeType = attributeType;
        }

        @Override
        public void handleEvent(MyEvent e, Charakter character, Trait trait) {
            if (e.getType() == EventType.ATTRIBUTECHANGE) {
                AttributeModification attributeModification = (AttributeModification) e.getSource();
                if (attributeModification.getAttributeType() == attributeType && attributeModification.getBaseAmount() < 0) {
                    attributeModification.setCancelled(true);
                }
            }
        }
    }
}
