//nobundling
// there are multiple modes to add as header: //nobundling //native //npm //nodejs
// https://www.windmill.dev/docs/getting_started/scripts_quickstart/typescript#modes

// import { toWords } from "number-to-words@1"
import puppeteer from 'puppeteer';

// fill the type, or use the +Resource type to get a type-safe reference to a resource
// type Postgresql = object


export async function main(
    url: string,
    city: string,
) {
    const browser = await puppeteer.connect({
        browserWSEndpoint: 'ws://192.168.2.72:3000',
    });

    try {
        const page = await browser.newPage();
        await page.goto(url, { waitUntil: 'domcontentloaded' });

        // Wait for the "均价排名" text to appear
        const hasText = await page.waitForFunction(() => {
            return document.body.innerText.includes('均价排名');
        }, { timeout: 1000 }).catch(() => false);

        if (!hasText) {
            throw new Error('"均价排名" text not found on the page.');
        }

        console.log('"均价排名" text is visible on the page.');

        // Loop to click "展开更多" until it no longer exists
        while (true) {
            const element = await page.$('div.more > span.taro-text');
            if (element) {
                console.log('Element <span class="taro-text">展开更多</span> exists on the page.');
                await element.click();
            } else {
                console.log('Element <span class="taro-text">展开更多</span> does not exist on the page.');
                break;
            }
        }

        // Extract content using the specified class
        const content = await page.evaluate(() => {
            const element = document.querySelector('.rate');
            return element ? element.outerHTML : null;
        });

        return { content, city };
    } catch (error) {
        console.error(`Error using Puppeteer: ${error.message}`);
        throw error;
    } finally {
        await browser.disconnect();
    }
}