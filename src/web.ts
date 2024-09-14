import { WebPlugin } from '@capacitor/core';

import type { CapacitorWebviewUpdatePlugin } from './definitions';

export class CapacitorWebviewUpdateWeb extends WebPlugin implements CapacitorWebviewUpdatePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
