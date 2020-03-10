package com.github.design.memoto;

public class Game {

  private MemEntity mEntity;

  public void start() {
    System.out.println("游戏开始了.当前生命值:" + mEntity.level);
  }

  public void quit() {
    System.out.println("游戏结束了.");
  }

  public MemEntity saveGame() {
    MemEntity saveMem = new MemEntity();
    saveMem.level = mEntity.level;
    saveMem.lifeLevel = mEntity.lifeLevel;
    saveMem.magicLevel = mEntity.magicLevel;
    return saveMem;
  }

  public void restoreGame(MemEntity mem) {
    mEntity.level = mem.level;
    mEntity.lifeLevel = mem.lifeLevel;
    mEntity.magicLevel = mem.magicLevel;
  }
}
