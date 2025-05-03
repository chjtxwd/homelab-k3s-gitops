//nobundling
// there are multiple modes to add as header: //nobundling //native //npm //nodejs
// https://www.windmill.dev/docs/getting_started/scripts_quickstart/typescript#modes

// import { toWords } from "number-to-words@1"
import puppeteer from 'puppeteer';

// fill the type, or use the +Resource type to get a type-safe reference to a resource
// type Postgresql = object

export async function main(
    url: string,
    vegetable: string,
    province: string,
) {
    const browser = await puppeteer.connect({
        browserWSEndpoint: 'ws://192.168.2.72:3000',
    });

    try {
        const page = await browser.newPage();
        await page.goto(url, { waitUntil: 'domcontentloaded' });

        // Check if the element with class "quotation-content" exists
        const quotationElement = await page.$('.quotation-content');
        if (!quotationElement) {
            throw new Error('Element with class "quotation-content" not found on the page.');
        }

        // Extract content from elements with class "quotation-content"
        const quotations = await page.$$eval('.quotation-content', elements => 
            elements.map(el => el.textContent?.trim())
        );

        return { quotations };
    } catch (error) {
        console.error(`Error using Puppeteer: ${error.message}`);
        throw error;
    } finally {
        await browser.disconnect();
    }
}