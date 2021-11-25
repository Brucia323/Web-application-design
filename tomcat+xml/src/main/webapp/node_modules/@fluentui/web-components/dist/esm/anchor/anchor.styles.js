import { css } from '@microsoft/fast-element';
import { AccentButtonStyles, baseButtonStyles, HypertextStyles, LightweightButtonStyles, OutlineButtonStyles, StealthButtonStyles, } from '../styles/';
import { appearanceBehavior } from '../utilities/behaviors';
const interactivitySelector = '[href]';
const nonInteractivitySelector = ':not([href])';
export const anchorStyles = (context, definition) => css `
    :host .control:not([href]) {
      cursor: default;
    }

    ${baseButtonStyles(context, definition, interactivitySelector, nonInteractivitySelector)}
  `.withBehaviors(appearanceBehavior('accent', AccentButtonStyles(context, definition, interactivitySelector, nonInteractivitySelector)), appearanceBehavior('hypertext', HypertextStyles(context, definition, interactivitySelector, nonInteractivitySelector)), appearanceBehavior('lightweight', LightweightButtonStyles(context, definition, interactivitySelector, nonInteractivitySelector)), appearanceBehavior('outline', OutlineButtonStyles(context, definition, interactivitySelector, nonInteractivitySelector)), appearanceBehavior('stealth', StealthButtonStyles(context, definition, interactivitySelector, nonInteractivitySelector)));
