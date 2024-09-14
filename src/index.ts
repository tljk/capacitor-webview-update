import { registerPlugin } from '@capacitor/core';

import type { CapacitorWebviewUpdatePlugin } from './definitions';

const CapacitorWebviewUpdate = registerPlugin<CapacitorWebviewUpdatePlugin>('CapacitorWebviewUpdate');

export * from './definitions';
export { CapacitorWebviewUpdate };
