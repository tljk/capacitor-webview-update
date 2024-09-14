import { registerPlugin } from '@capacitor/core';

import type { CapacitorWebviewUpdatePlugin } from './definitions';

const CapacitorWebviewUpdate = registerPlugin<CapacitorWebviewUpdatePlugin>('CapacitorWebviewUpdate', {
  web: () => import('./web').then(m => new m.CapacitorWebviewUpdateWeb()),
});

export * from './definitions';
export { CapacitorWebviewUpdate };
