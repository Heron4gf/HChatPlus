package it.heron4gf.hchatplus.checks;

public interface CanReplace {
    boolean replacementIsEnabled();
    String replace(String message);
}
